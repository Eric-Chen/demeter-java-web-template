package com.template.tools;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

/**
 * Created by eric on 3/18/16.
 */
public class ConcurrentTaskRunner implements InitializingBean, DisposableBean {

    private static final int DEFAULT_HANDLE_SET_SIZE = 8;

    private ForkJoinPool forkJoinPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        int parallelism = Runtime.getRuntime().availableProcessors() * 2;
        forkJoinPool = new ForkJoinPool(parallelism);
    }

    @Override
    public void destroy() throws Exception {
        if(forkJoinPool != null){
            forkJoinPool.shutdown();
        }
    }

    public <T> void concurrentConsumeData(Collection<T> data, Consumer<T> consumer){
        if(CollectionsUtil.notNullOrEmpty(data)){
            if(data.size() < DEFAULT_HANDLE_SET_SIZE){
                CollectionsUtil.forEach(data, consumer);
            } else {
                List<T> listData = Lists.newArrayList(data);
                forkJoinPool.invoke(new SubTask<>(listData, consumer));
            }
        }
    }

    private static class SubTask<E> extends RecursiveAction {

        private List<E> data;

        private Consumer<E> consumer;

        public SubTask(List<E> data, Consumer<E> consumer) {
            this.data = data;
            this.consumer = consumer;
        }

        @Override
        protected void compute() {
            if(CollectionsUtil.notNullOrEmpty(this.data)){
                int size = this.data.size();
                if(size > DEFAULT_HANDLE_SET_SIZE){
                    SubTask<E> left = new SubTask<>(this.data.subList(0, DEFAULT_HANDLE_SET_SIZE), this.consumer);
                    SubTask<E> right = new SubTask<>(this.data.subList(DEFAULT_HANDLE_SET_SIZE, size), this.consumer);
                    invokeAll(left, right);
                } else {
                    CollectionsUtil.forEach(this.data, this.consumer);
                }
            }
        }
    }

//    public static void main(String[] args) throws Exception {
//        ConcurrentTaskRunner runner = new ConcurrentTaskRunner();
//        runner.afterPropertiesSet();
//        List<Integer> list = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13);
//        AtomicInteger sum1 = new AtomicInteger();
//        long start1 = System.nanoTime();
//        CollectionsUtil.forEach(list, (one) -> {sum1.addAndGet(one);});
//        System.out.println((System.nanoTime() - start1)/1000000);
//        System.out.println(sum1);
//        AtomicInteger sum = new AtomicInteger();
//        long start2 = System.nanoTime();
//        runner.concurrentConsumeData(list, (one) -> {
//            sum.addAndGet(one);
//        });
//        System.out.println((System.nanoTime() - start2)/1000000);
//        System.out.println(sum.get());
//        runner.destroy();
//    }

}
