package com.demeter.framework.aop.performance;

import com.demeter.tools.CollectionsUtil;
import com.demeter.tools.TimeUtils;
import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eric on 12/15/15.
 */

@Aspect
public class PerformanceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger("PERFORMANCE");

    private ThreadLocal<LinkedList<String>> callStackContainer = new ThreadLocal<>();

    @Around("profilingMethodsCall()")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {

        LinkedList<String> callStack = this.callStackContainer.get();
        if(CollectionsUtil.isNullOrEmpty(callStack)){
            callStack = Lists.newLinkedList();
            callStackContainer.set(callStack);
        }
        String targetMethod = joinPoint.getTarget().getClass().getSimpleName()+"."+ joinPoint.getSignature().getName();
        callStack.push("{");
        callStack.push(targetMethod);
        int callDepth = countLevel(callStack);
        LOGGER.info("target: {} {} start", padLeftSpace(callDepth), targetMethod);
        TimeUtils.StopWatch sw = TimeUtils.StopWatch.start();
        try {
            return joinPoint.proceed();
        } finally {
            long taskInMillis = sw.stop();
            callStack.poll();
            callDepth = countLevel(callStack);
            callStack.poll();
            LOGGER.info("target: {} {}.{} end, time: {} ms", padLeftSpace(callDepth), joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), taskInMillis);
        }
    }

    private int countLevel(List<String> stack){
        int count = 0;
        for(String level : stack){
            if("{".equals(level)){
                count++;
            }
        }
        return count;
    }

    private String padLeftSpace(int count){
        StringBuilder spaces = new StringBuilder();
        for(int i=0; i<count; i++){
            spaces.append("-->");
        }
        return spaces.toString();
    }

    @Pointcut("execution(* com.kuaihuoyun.server.zeus..*.*(..))")
    public void profilingMethodsCall(){}

}
