package com.app.overboxsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.app.overboxsample.network.interfaces.IViewCallback;
import com.app.overboxsample.providers.AppProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {


    AppProvider appProvider;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // setContentView(R.layout.content_launcher);
        appProvider = new AppProvider();
       // ButterKnife.inject(this);
      //  listview = (ListView) findViewById(R.id.listView);

        fetchCategory(imei_check.categoryId);


    }

    @Override
    protected void onStart() {
        super.onStart();
        appProvider.attach();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        appProvider.detach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @Override
    public void onClick(View view) {

    }



    public List[] parseJson(JSONObject data) {

        List<String> stringValueList = new ArrayList<String>();//stores the string containing values of the object
        List<String> stringKeyList = new ArrayList<String>();//stores the ayyar of al key fields to be prited as a sheet


        if (data != null) {
            Iterator<String> it = data.keys();


//                try {
//                    while (it.hasNext()) {
//
//                        String key = it.next();
//                        Log.d("printjsaon key",key);
//                        stringKeyList.add(key);
//                        stringValueList.add(data.getString(key));
//                    }
//
//                }
//                catch (Exception e) {
//
//
//
//                }
            try {

                for (int i = 0; i < data.names().length(); i++) {
                    stringKeyList.add(data.names().getString(i));
                    Log.d("parsejson key",data.names().getString(i));
                    stringValueList.add(String.valueOf(data.get(data.names().getString(i))));



                }
            }
            catch (Exception e){}

        }

        return new List[]{stringValueList, stringKeyList};
    }



    public void fetchCategory(String categoryId) {


        appProvider.fetchCategoryDetails(categoryId,new IViewCallback<JSONObject>() {


            @Override
            public void onSuccess(JSONObject dataObject) {

                try{
                    JSONObject jb = null;
                    JSONObject jb1 = null;

                     Log.d("Json object received", String.valueOf(dataObject));


                    JSONArray value= (JSONArray) dataObject.get("data");
                    jb = (JSONObject)value.getJSONObject(0);

                    Log.d("Json String", String.valueOf(jb));
                    String r = jb.getString("object_result");

                    Log.d("Json String",r);


                    jb1 = new JSONObject(r);
                    Log.d("printjb1", String.valueOf(jb1));

                    Log.d("printr",r);
                    imei_check.formDisplayingObject = parseJson(jb1);//error

                    Log.d("formDisplayingbject", String.valueOf(imei_check.formDisplayingObject[1]));


                    if(imei_check.isProductPresent)
                    {
                    Intent i=new Intent(LauncherActivity.this,Display_form.class);
                    startActivity(i);
                    }

                    else
                    {
                        Intent i=new Intent(LauncherActivity.this,fetch_category.class);
                        startActivity(i);}


                    //creates the value of object result as the new object

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //parses and print the value




            }




            @Override
            public void onError(String errorMessage, int errorCode, @Nullable JSONObject dataObject) {
                //  setRefreshing(false);
            }
        });
    }
}
