package com.demeter.tools;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by eric on 3/11/16.
 */
public final class CollectionsUtil {

    private CollectionsUtil(){}

    public static boolean isNullOrEmpty(Collection<?> c){
        return c == null || c.isEmpty();
    }

    public static <K, V> boolean isNullOrEmpty(Map<K, V> map){
        return map == null || map.isEmpty();
    }

    public static boolean notNullOrEmpty(Collection<?> c){
        return !isNullOrEmpty(c);
    }

    public static <K, V> boolean notNullOrEmpty(Map<K, V> map){
        return !isNullOrEmpty(map);
    }

    public static <E> void forEach(Collection<E> c,  Consumer<? super E> action){
        if(isNullOrEmpty(c)){
            return;
        }
        for(E item : c){
            action.accept(item);
        }
    }

    public static <K, V> void forEach(Map<K, V> m, BiConsumer<K, V> consumer){
        if(isNullOrEmpty(m)){
            return;
        }
        for(Map.Entry<K, V> entry : m.entrySet()){
            consumer.accept(entry.getKey(), entry.getValue());
        }
    }

    public static class MapBuilder<K, V> {

        private Map<K, V> map;

        public static <K, V> MapBuilder<K, V> newHashMap(){
            MapBuilder<K, V> rs = new MapBuilder<>();
            rs.map = new HashMap<>();
            return rs;
        }

        public static <K, V> MapBuilder<K, V> newConcurrentHashMap(){
            MapBuilder<K, V> rs = new MapBuilder<>();
            rs.map = new ConcurrentHashMap<>();
            return rs;
        }

        public MapBuilder<K, V> put(K key, V value){
            this.map.put(key, value);
            return this;
        }

        public <T extends Map<K, V>> T build(){
            return (T) this.map;
        }

    }


//    public static void main(String[] args) {
//        List<Integer> items = Lists.newArrayList(1,3,4,5);
//        forEach(items, (i)->{System.out.println(i);});
//    }
}
