package com.app.overboxsample.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorUtils {
    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    private static ExecutorService networkExecutorService = Executors.newCachedThreadPool();

    private static ExecutorService singleThreadExecutorService = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());
    private static ExecutorService cacheExecutorService;

    public static void submitNetworkTask(Callable<Void> call) {
        networkExecutorService.submit(call);
    }

    public static void submitNetworkTask(ExecutorService executorService, Callable<Void> call) {
        executorService.submit(call);
    }

    public static void submitSingleThreadTask(Callable<Void> call) {
        singleThreadExecutorService.submit(call);
    }

    public static void postOnUiThread(Runnable runnable) {
        mainHandler.post(runnable);
    }

    public static void postDelayedOnUIThread(Runnable runnable, int delay) {
        mainHandler.postDelayed(runnable, delay);
    }

    public static void removeCallbacks(Runnable... runnables) {
        for(int i = 0; i < runnables.length; i++) {
            removeCallbacks(runnables[i]);
        }
    }

    public static void removeCallbacks(Runnable runnable) {
        mainHandler.removeCallbacks(runnable);
    }
}
