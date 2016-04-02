package com.demeter.framework.spring.aop.validation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by eric on 3/24/16.
 */
@Aspect
public class ValidationAspect implements InitializingBean {

    ExpressionParser parser;
    StandardEvaluationContext evaluationContext;


    @Override
    public void afterPropertiesSet() throws Exception {
        parser = new SpelExpressionParser();
        evaluationContext = new StandardEvaluationContext();
//        evaluationContext.registerFunction("length", StringUtils.class.getDeclaredMethod("length", String.class));
    }

    @Pointcut("execution(* com.testall.spring.*Api.*(..))")
    public void pointcutPattern(){}

    @Around("pointcutPattern()")
    public Object aspect(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> targetClazz = joinPoint.getTarget().getClass();
        EnableValidate enableValidate = targetClazz.getAnnotation(EnableValidate.class);
        if(enableValidate != null){
            System.out.println("testing........");
            Object[] args = joinPoint.getArgs();
            Class[] clazzArr = new Class<?>[args.length];
            for(int i=0; i < args.length; i++){
                clazzArr[i] = args[i].getClass();
            }
            Method targetMethod = targetClazz.getMethod(joinPoint.getSignature().getName(), clazzArr);
            Annotation[][] paramAnnotations = targetMethod.getParameterAnnotations();
            for(int i = 0; i< paramAnnotations.length; i++){
                for(int j = 0; j < paramAnnotations[i].length; j++){
                    if(paramAnnotations[i][j] instanceof ValidationRule){
                        ValidationRule validationRule = (ValidationRule)paramAnnotations[i][j];
                        String rule = validationRule.value();

                        evaluationContext.setVariable("value", args[i]);
                        Expression expression = parser.parseExpression(rule);
                        System.out.println("rule: " + rule + "======" + expression.getValue(evaluationContext, Boolean.class));
                    } else if(paramAnnotations[i][j] instanceof EnableValidate) {
                        Class<?> clz = args[i].getClass();
                        Field[] fields = clz.getDeclaredFields();
                        for(Field field : fields){
                            ValidationRule validationRule = field.getAnnotation(ValidationRule.class);
                            String rule = validationRule.value();
                            StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
                            field.setAccessible(true);
                            evaluationContext.setVariable("value", field.get(args[i]));
                            field.setAccessible(false);
                            Expression expression = parser.parseExpression(rule);
                            System.out.println("field rule: " + rule + "======" + expression.getValue(evaluationContext, Boolean.class));
                        }
                    }
                }
            }

            System.out.println(Arrays.toString(joinPoint.getArgs()));

        }
        return joinPoint.proceed();
    }

}
