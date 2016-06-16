package com.app.overboxsample;

import android.app.Application;
import android.content.Context;

/**
 * Created by himanshu on 12/03/16.
 */
public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }
}
