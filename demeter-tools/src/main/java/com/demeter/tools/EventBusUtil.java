package com.demeter.tools;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/**
 * Created by eric on 4/2/16.
 */
public class EventBusUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventBusUtil.class);

    private EventBusUtil() {}

    private static final EventBus EVENT_BUS = new AsyncEventBus(
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));


    public static void post(Object event) {
        LOGGER.info("Event register: {}", event);
        EVENT_BUS.post(event);
    }

    public static void registerService(Object service){
        if(service == null){
            throw new NullPointerException("Register null service, please check");
        }
        LOGGER.info("[EventBus] register service: {}", service.getClass());
        EVENT_BUS.register(service);
    }

}
