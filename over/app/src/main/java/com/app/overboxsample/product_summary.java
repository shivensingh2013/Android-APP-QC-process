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

public class product_summary extends AppCompatActivity implements View.OnClickListener {


    AppProvider appProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.initial_summary);

        appProvider=new AppProvider();


        try {

            //parsing the data from Productonupload dataobject
            JSONArray value = (JSONArray) imei_check.productOnUpload.get("data");
            JSONObject job = (JSONObject) value.get(0);
            imei_check.productOnUpload=job;
            imei_check.categoryId=job.getString("category_id");

            Log.d("Sql initial data", String.valueOf(job));

            JSONObject convert= new JSONObject();
            convert.put("IMEI",job.get("imei"));
            convert.put("Client SKU",job.get("sku"));
            convert.put("Client Name",job.get("clients"));
            convert.put("Model Number",job.get("model_number"));
            convert.put("Brand",job.get("brands"));
            convert.put("Product Name",job.get("products_name"));
            ;



            if (convert!= null) {
                int c = 0;
                Iterator<String> it = convert.keys();
                while (it.hasNext())

                {
                    c++;
                    String key = it.next();
                    String val = convert.getString(key);
                    String id = "textView" + c;
                    int resID = getResources().getIdentifier(id, "id", getPackageName());
                    TextView a = (TextView) findViewById(resID);
                    a.setText(key);


                    c++;
                    id = "textView" + c;
                    resID = getResources().getIdentifier(id, "id", getPackageName());
                    a = (TextView) findViewById(resID);
                    a.setText(convert.getString(key));


                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }





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
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.SQCbutton:
                imeiCheckFunc();
                break;
        }

    }


    public void imeiCheckFunc() {

        String h = String.valueOf(imei_check.imeival.getText());


        appProvider.fetch_Imei(imei_check.imeival, new IViewCallback<JSONObject>() {


            @Override
            public void onSuccess(JSONObject dataObject) {

                String val = null;


                try {

                    Log.d("initial_summary", dataObject.getString("data"));
                    if (dataObject.getBoolean("success")) {

                        imei_check.isProductPresent=true;
                        JSONArray buf=(JSONArray)dataObject.get("data");
                        imei_check.productAllDetails= (JSONObject)buf.get(0);
                        Log.d("data present", String.valueOf(imei_check.productAllDetails));
                        Toast.makeText(getApplicationContext(), "Display filled form ", Toast.LENGTH_SHORT).show();

                    } else

                    {
                        imei_check.isProductPresent=false;
                        Log.d("going","tonew form");
                        Toast.makeText(getApplicationContext(), "displaying new form", Toast.LENGTH_SHORT).show();

                    }


                    Intent inte = new Intent(product_summary.this, LauncherActivity.class);
                    startActivity(inte);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            @Override
            public void onError(String errorMessage, int errorCode, @Nullable JSONObject dataObject) {
                Log.d("onerror", "value error in response imei not a jsonobject");


            }


        });

    }
}