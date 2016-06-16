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


    @InjectView(R.id.label_categories) TextView categoriesJsonDump;//text view bindng here
   // @InjectView(R.id.label_category) TextView categoryJsonDump;
//    @InjectView(R.id.btn_fetch_categories) Button fetchCategoriesButton;


   // @InjectView(R.id.btn_fetch_category_details) Button fetchCategoryButton;
   // @InjectView(R.id.et_category_id) EditText etCategoryId;
    List category_id_array;
    public static List[] object;

    AppProvider appProvider;
     ListView listview;
   public  static  int  category_id;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_launcher);

        appProvider = new AppProvider();

        ButterKnife.inject(this);                                           //injects the


         listview = (ListView) findViewById(R.id.listView);
        fetchCategories();

    }





    @Override
    protected void onStart() {
        super.onStart();
        appProvider.attach();
    }

    @Override
    protected void onResume() {
        super.onResume();

      //  fetchCategoriesButton.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

    //    fetchCategoriesButton.setOnClickListener(null);
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
//        switch (view.getId()) {
//            case R.id.btn_fetch_categories:
             //   fetchCategories();
                //break;

        //}
    }

//    private void setRefreshing(final boolean isRefreshing) {
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                if (swipeRefreshLayout == null) return;
//                swipeRefreshLayout.setRefreshing(isRefreshing);
//            }
//        });
//    }



    private List[] parseJson(JSONObject data) {

        ArrayList<String> stringArrayList = new ArrayList<String>();//stores the string containing values of the object
        ArrayList<String> stringKeyList = new ArrayList<String>();//stores the ayyar of al key fields to be prited as a sheet

        JSONObject obj = null;

        if (data != null) {
            Iterator<String> it = data.keys();

            while (it.hasNext()) {


                String key = it.next();
                try {

                    obj = new JSONObject(String.valueOf(data.get(key)));//creates the object of the value passsing as a string
//
                }

                catch (JSONException e) {

                }


                try {


                    if (obj instanceof JSONObject) {
                        stringKeyList.add(key);
                        stringArrayList.add(data.getString(key));



                    } else {
                        stringArrayList.add(data.getString(key));
                        stringKeyList.add(key);

                    }
                } catch (Throwable e) {


                }
            }
        }

        return new List[]{stringArrayList, stringKeyList};
    }







    private void fetchCategories() {



        //the fetch categories will call the function in app provider
        appProvider.fetchCategories(new IViewCallback<JSONObject>()
        {

            @Override
            public void onSuccess(JSONObject dataObject) {

                //pretty printing jason
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(dataObject.toString());
                String prettyJsonString = gson.toJson(je);
                Log.d("brand works ",prettyJsonString);

                List[] obj = parseJson(dataObject);
                List values = obj[0];

                category_id_array = obj[1];
                Log.d("list", String.valueOf(category_id_array));

                adapter = new ArrayAdapter<String>(LauncherActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, values);



                if (dataObject == null) return;

                // Assign adapter to ListView
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        // ListView Clicked item index
                        int itemPosition = position;

                        fetchCategory((String) category_id_array.get(position));

                    }

                });


            }

            @Override
            public void onError(String errorMessage, int errorCode, @Nullable JSONObject dataObject)
            {

            }
        });
    }







    private void fetchCategory(String categoryId) {
//        setRefreshing(true);

        IViewCallback<String> io2=new IViewCallback<String>() {
            @Override
            public void onSuccess(String dataObject) {

//                setRefreshing(false);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(dataObject.toString());
                String prettyJsonString = gson.toJson(je);
                Log.d("the category we chooose",prettyJsonString);



                JSONObject jb = null;

                try {
                    JSONArray array = new JSONArray(dataObject);
                    jb = (JSONObject)array.getJSONObject(0);
                    Log.d("the phase1", String.valueOf(array));
                    Log.d("the phase2", String.valueOf(jb));
                    //converts the data into a normal object now containing object itself


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject jb1 = null;


                try {

                    String r = jb.getString("object_result");
                    jb1 = new JSONObject(r);

                    //creates the value of object result as the new object

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                object =   parseJson(jb1); //parses and print the value




                if(dataObject == null) return;
                Intent i = new Intent(LauncherActivity.this, fetch_category.class);

                startActivity(i);

            }




            @Override
            public void onError(String errorMessage, int errorCode, @Nullable String dataObject) {
              //  setRefreshing(false);
            }
        };

        appProvider.fetchCategoryDetails(categoryId,io2);
    }
}
