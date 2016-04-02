package com.demeter.tools;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by eric on 4/2/16.
 */
public final class OplogTracker {

    public static final String TRACK_USERNAME = "username";

    public static final String TRACK_UID = "uid";

    public static final String TRACK_MODULE_NAME = "module";

    public static final String TRACK_OPERATION_NAME = "operation";


    private static ThreadLocal<Map<String, Object>> localMap = new ThreadLocal<>();

    public static void captureBasicLogInfo(Map<String, Object> logInfo){
        Map<String, Object> infoMap = localMap.get();
        if(CollectionsUtil.isNullOrEmpty(infoMap)){
            infoMap = Maps.newHashMap();
            localMap.set(infoMap);
        }
        infoMap.putAll(logInfo);
    }

    public static Map<String, Object> getLocalMap(){
        return localMap.get();
    }

    public static void release(){
        localMap.remove();
    }

}
