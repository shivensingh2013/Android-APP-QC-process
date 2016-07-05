/**
 * Created by root on 6/9/16.
 */



package com.app.overboxsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.overboxsample.network.interfaces.IViewCallback;
import com.app.overboxsample.providers.AppProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class imei_check extends AppCompatActivity implements View.OnClickListener{

    public static List[] formDisplayingObject;
    public static EditText imeival;
    public static JSONObject productOnUpload;
    public static  JSONObject productAllDetails;
    public static boolean isProductPresent;
    public static String categoryId;
    AppProvider appProvider;


@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.imei_display);
        imeival=(EditText) findViewById(R.id.editText);
        appProvider = new AppProvider();


}
    @Override
    protected void onStart() {
        super.onStart();
        appProvider.attach();
    }

    @Override
    protected void onStop() {
        super.onStop();
        appProvider.detach();
    }


    @Override
    public void onClick(View view)
    {

        switch (view.getId()) {
            case R.id.button:
                getProductOnUpload();
                break;
        }

    }


    public void getProductOnUpload()
    {

        appProvider.getInitialImei(imei_check.imeival, new IViewCallback<JSONObject>() {


            @Override
            public void onSuccess(JSONObject dataObject) {

                imei_check.productOnUpload=dataObject;

                Intent j = new Intent(imei_check.this, product_summary.class);
                startActivity(j);

            }


            @Override
            public void onError(String errorMessage, int errorCode, @Nullable JSONObject dataObject) {
                Toast.makeText(getApplicationContext(), "product not uploaded yet", Toast.LENGTH_LONG).show();



            }


        });






    }


}