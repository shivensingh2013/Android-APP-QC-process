package com.app.overboxsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.app.overboxsample.network.interfaces.IViewCallback;
import com.app.overboxsample.providers.AppProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;

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

                try{

                    imei_check.formDisplayingObject = parseJson(imei_check.productAllDetails);

                    if(imei_check.isProductPresent)
                    {
                        Log.d("Displayis","a");
                        Intent i=new Intent(LauncherActivity.this,QC_Report.class);
                        startActivity(i);
                    }

                    else
                    {
                        Log.d("FetchCategory","is");
                        Intent i=new Intent(LauncherActivity.this,fetch_category.class);
                        startActivity(i);
                    }


                    //creates the value of object result as the new object

                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                //parses and print the value




            }





}
