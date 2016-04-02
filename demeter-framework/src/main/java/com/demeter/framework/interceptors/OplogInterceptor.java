package com.demeter.framework.interceptors;

import com.demeter.tools.OplogTracker;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.demeter.tools.CollectionsUtil.MapBuilder;

/**
 * Created by eric on 4/2/16.
 */
public class OplogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqMethod = request.getMethod();
        if("POST".equalsIgnoreCase(reqMethod)){
            HttpSession session = request.getSession(false);
            if(session != null){
                String username = (String)session.getAttribute("username");
                Integer uid = (Integer) session.getAttribute("uid");
                //fixme 拆分请求路径,跟权限表里面的path做对照,以获取模块名
                String module = request.getRequestURI();
                MapBuilder<String, Object> basicInfoBuilder = MapBuilder.newHashMap();
                basicInfoBuilder.put(OplogTracker.TRACK_USERNAME, username).put(OplogTracker.TRACK_UID, uid)
                        .put(OplogTracker.TRACK_MODULE_NAME, module);
                OplogTracker.captureBasicLogInfo(basicInfoBuilder.build());
            }

        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        OplogTracker.release();
        super.postHandle(request, response, handler, modelAndView);
    }
}
