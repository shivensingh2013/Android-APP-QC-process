package com.app.overboxsample.network.volley;

import com.android.volley.ExecutorDelivery;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.NoCache;
import com.app.overboxsample.App;

import java.io.File;
import java.util.concurrent.Executors;

public class VolleyQueueUtils {

    private static final String DEFAULT_CACHE_DIR = "volley";
    private static final int DISK_CACHE_MAX_SIZE = 20 * 1024 * 1024;

    private static RequestQueue sGeneralRequestQueue;

    private static DiskBasedCache sDiskCache;

    private static RequestQueue sImageQueue;

//    private static ImageLoader sImageLoader;

    private static RequestQueue sJobQueue;

    private static RequestQueue sSingleThreadedRequestQueue;

    static {
        File cacheDir = new File(App.context.getCacheDir(), DEFAULT_CACHE_DIR);
        sDiskCache = new DiskBasedCache(cacheDir, DISK_CACHE_MAX_SIZE);

        ResponseDelivery delivery = new ExecutorDelivery(Executors.newFixedThreadPool(4));
        ResponseDelivery deliverySingle = new ExecutorDelivery(Executors.newFixedThreadPool(1));

        sGeneralRequestQueue =
                new RequestQueue(sDiskCache, new BasicNetwork(new OkHttpStack(App.context)), 4, delivery);

//        sGeneralRequestQueue = Volley.newRequestQueue(App.context, new OkHttpStack(App.context));
        sGeneralRequestQueue.start();

        sImageQueue = new RequestQueue(sDiskCache ,new BasicNetwork(new OkHttpStack(App.context)), 4, delivery);
        sImageQueue.start();

        sSingleThreadedRequestQueue = new RequestQueue(sDiskCache, new BasicNetwork(new OkHttpStack(App.context)), 1,
                deliverySingle);
        //sSingleThreadedRequestQueue.start();

//        sImageLoader = new ImageLoader(sImageQueue, new LruBitmapCache());

        // Job queue for background tasks
        sJobQueue = new RequestQueue(new NoCache(), new BasicNetwork(new OkHttpStack(App.context)), 4, delivery);
        sJobQueue.start();
    }

    public static RequestQueue getGeneralRequestQueue() {
        return sGeneralRequestQueue;
    }

    public static RequestQueue getSingleThreadedRequestQueue() {
        return sSingleThreadedRequestQueue;
    }

    public static RequestQueue getJobQueue () {
        return sJobQueue;
    }
}

