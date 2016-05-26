package com.android.housingonitoringagent.homeworryagent.utils;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    // 默认线程数
    final static int DEFAULT_THREAD_COUNT = 5;
    // 默认线程池，一般用来执行网络操作
    public static ExecutorService defPool = newFixedPool();

    // 本地IO线程数
    final static int LOCAL_IO_THREAD_COUNT = 1;
    // 本地IO线程池，一般用来执行网络操作
    public static ExecutorService localIOPool = newLocalIOPool();

    public static ExecutorService newFixedPool() {
        return Executors.newFixedThreadPool(DEFAULT_THREAD_COUNT);
    }

    public static ExecutorService newLocalIOPool() {
        return Executors.newFixedThreadPool(LOCAL_IO_THREAD_COUNT);
    }

    public static void restart() {
        ExecutorService oldDefPool = defPool;
        ExecutorService oldLocalIOPool = localIOPool;

        defPool = newFixedPool();
        localIOPool = newLocalIOPool();

        oldDefPool.shutdownNow();
        oldLocalIOPool.shutdownNow();
    }
}
