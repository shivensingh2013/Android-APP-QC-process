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
import android.widget.Toast;

import com.app.overboxsample.network.interfaces.IViewCallback;
import com.app.overboxsample.providers.AppProvider;

import org.json.JSONException;
import org.json.JSONObject;

public class imei_check extends AppCompatActivity implements View.OnClickListener{



    AppProvider appProvider;
    EditText imeival;

@Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.imei_display);

        imeival=(EditText) findViewById(R.id.editText);


        appProvider = new AppProvider();
        Toast.makeText(getApplicationContext(), "imei check starting", Toast.LENGTH_SHORT).show();


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

                Toast.makeText(getApplicationContext(), "imei check starting", Toast.LENGTH_SHORT).show();

                imeiCheckFunc();
                break;
        }

    }


public void imeiCheckFunc()
{

    String h=String.valueOf(imeival.getText());
    JSONObject a=new JSONObject();
    try {
        a.put("imei", h);
    } catch (JSONException e) {
        e.printStackTrace();
    }



    appProvider.fetch_Imei(a, new IViewCallback<JSONObject>() {


        @Override
        public void onSuccess(JSONObject dataObject) {

            String val = null;
            Log.d("Mongo data",String.valueOf(dataObject));
            Intent j = new Intent(imei_check.this, fetch_category.class);
            startActivity(j);

        }


        @Override
        public void onError(String errorMessage, int errorCode, @Nullable JSONObject dataObject) {
            Log.d("onerror", "Mongo");
            Toast.makeText(getApplicationContext(), "error no success", Toast.LENGTH_SHORT).show();

        }


    });

}
}