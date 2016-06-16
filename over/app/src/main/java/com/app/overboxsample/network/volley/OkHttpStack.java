package com.app.overboxsample.network.volley;

import android.content.Context;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An {@link com.android.volley.toolbox.HttpStack HttpStack} implementation
 * which uses OkHttp as its transport.
 */
public class OkHttpStack extends HurlStack {
    private final OkUrlFactory  mFactory;
    private final OkHttpClient mClient;

    public OkHttpStack(Context context) {
        this(new OkHttpClient(), context);
    }

    public OkHttpStack(OkHttpClient client, Context context) {
        if (client == null) {
            throw new NullPointerException("Client must not be null.");
        }
        mClient = client;
        mFactory = new OkUrlFactory(client);
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return mFactory.open(url);
    }
}
