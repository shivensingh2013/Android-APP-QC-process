package com.app.overboxsample;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.overboxsample.network.interfaces.IViewCallback;
import com.app.overboxsample.providers.AppProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

public class fetch_category extends AppCompatActivity {

    public static List lt;

    JSONObject UploadQCData=new JSONObject();
    String main_key;
    int id = 0;
    int json_var = 0;
    int auto_var = 0;   //used to map TextView with AutoTextComplete
    JSONObject Value1;
    AutoCompleteTextView auto;
    String type;
    LinearLayout lm;

    HashMap<Integer,String> map = new HashMap<Integer,String>();
    HashMap<Integer,String> map_key = new HashMap<Integer,String>();
    ArrayList map_autocomplete = new ArrayList();

    AppProvider appProvider;
    ArrayAdapter<String> adapter1;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetch_category);
        appProvider = new AppProvider();

        ButterKnife.inject(this);
        List values =  imei_check.formDisplayingObject[0];
        List keys =  imei_check.formDisplayingObject[1];


        lm = (LinearLayout) findViewById(R.id.linearMain);

        for(int i=0;i<keys.size();i++)
        {
             main_key = (String) keys.get(i);    //get key value
             String main_value  = (String) values.get(i);    //get array value to be read

            try {
                 Value1 = new JSONObject(main_value);
                  type = Value1.getString("type");
                 }
            catch (JSONException e) {
                e.printStackTrace();
                 }





            final LinearLayout ll = new LinearLayout(this);

            TextView product = new TextView(this);
            product.setId(id);
            product.setText(main_key+"");
            product.setTextSize(20);
            product.setPadding(0, 10, 0, 10);
            product.setTextColor(Color.parseColor("#0e6655"));

            product.setGravity(View.TEXT_ALIGNMENT_CENTER);
            lm.addView(product);



            map_key.put(id, main_key);     //add key value to map
            id = id+1;


            if((type.contains("autocomplete")))
            {
                map_autocomplete.add(id);
           //     id = id+1;

            }


            if(type.equals("text")) {
                String set_data="";

                EditText edt = new EditText(this);
                try {
                    set_data = Value1.getString("set");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (set_data.equals("date")) {
                     set_data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    edt.setText(set_data);
                }


                else if(main_key.equals("imei"))
                {
                    Log.d("Tag","Going_to_imei");
                    try {
                        set_data = (String) (imei_check.productOnUpload).get("imei");
                        Log.d("Tag_Imei", String.valueOf(imei_check.productOnUpload));
                        edt.setText(set_data);
                    } catch (JSONException e) {
                        Log.d("Error","Error");
                        e.printStackTrace();
                    }
                }

                else if(main_key.equals("Model_Number"))
                {

                    try {
                        set_data = (String) (imei_check.productOnUpload).get("model_number");
                        edt.setText(set_data);
                        }
                    catch (JSONException e) {
                        Log.d("Error","Error");
                        e.printStackTrace();
                    }
                }
                else if(set_data !=null)
                {
                    edt.setText(set_data);
                }
                map.put(id, "text");
                edt.setId(id);
                id =id+ 1;
//
                edt.setPadding(40, 10, 40, 10);
                edt.setBackgroundColor(Color.parseColor("#fbfcfc"));
//
                lm.addView(edt);
            }
//
            //if radio in value we have to make it a obje ArrayList<String> options = new ArrayList<String>();ct



         else {
                if (type.contains("dropdown")) {
                    ArrayList<String> options = new ArrayList<String>();



                   // Log.d("Tag_Dropdown","Dropdown");
                    Spinner mSpinner = new Spinner(this);
                    map.put(id, "dropdown");
                    mSpinner.setId(id);
                    id =id+1;


                    JSONObject jsonObj = null;
                    String r = null;
                    try {
                        jsonObj = new JSONObject(main_value);
                        r = jsonObj.getString("set");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray array = null;
                    try {
                        array = new JSONArray(r);
                        Log.d("Tag_dropdown", String.valueOf(array));
                        for (int j = 0; j < array.length(); j++) {
                            options.add((String) array.get(j));
                                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options);
                    mSpinner.setAdapter(adapter);

                    mSpinner.setPadding(40, 10, 40, 10);
                    mSpinner.setBackgroundColor(Color.parseColor("#fbfcfc"));
                    lm.addView(mSpinner);


                } else if (type.contains("radio")) {


                    JSONObject jsonObj = null;
                    String r = null;
                    try {
                        jsonObj = new JSONObject(main_value);
                        r = jsonObj.getString("set");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray array = null;
                    try {
                        array = new JSONArray(r);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final RadioButton[] rb = new RadioButton[array.length()];
                    RadioGroup rg = new RadioGroup(this); //create the RadioGroup
                    map.put(id, "radio");  //add radio group to map
                    rg.setId(id);
                    id = id + 1;
//
                    rg.setOrientation(RadioGroup.VERTICAL);


                    for (int j = 0; j < array.length(); j++) {
//
                        try {
//
                            rb[j] = new RadioButton(this);
                            rb[j].setId(id);
                            id = id + 1;
                            rg.addView(rb[j]); //the RadioButtons are added to the radioGroup instead of the layout

                            rb[j].setText(String.valueOf(array.get(j)) + "");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (rg.getParent() != null)
                            ((LinearLayout) rg.getParent()).removeView(rg);
                        rg.setPadding(40, 10, 40, 10);
                        rg.setBackgroundColor(Color.parseColor("#fbfcfc"));

                        lm.addView(rg);

                    }
                } else if (type.contains("autocomplete")) {
                    try {

                        JSONObject obj = null;
                        obj = new JSONObject(main_value);
                        String ap = "";
                        ap = obj.getString("set");


                        appProvider.fetchval(ap, new IViewCallback<String>()

                        {
                            @Override
                            public void onSuccess(String dataObject) {
                                JSONArray a = null;

                                try {
                                    a = new JSONArray(dataObject);
                                    //   List<String> start = new ArrayList<String>();
                                    //we are creating a temporary spinner named variable .After adding this to the ll layout the name of spinner is forgoteten and so can be used again.

                                    auto = (AutoCompleteTextView) new AutoCompleteTextView(fetch_category.this);

                                    map.put(id, "autocomplete");
                                    auto.setId(id);
                                    id = id + 1;
//
                                    JSONObject jb = null;
                                    lt = new ArrayList();
                                    for (int j = 0; j < a.length(); j++) {
                                        jb = (JSONObject) a.getJSONObject(j);
                                        lt.add(String.valueOf(jb.get("name")));
                                        }

                                    adapter1 = new ArrayAdapter<String>(fetch_category.this
                                            , R.layout.support_simple_spinner_dropdown_item, lt);
                                    adapter1.setDropDownViewResource
                                            (android.R.layout.simple_spinner_dropdown_item);

                                    auto.setAdapter(adapter1);
                                    auto.setThreshold(1);
                                    auto.setDropDownWidth(500);
                                    if (auto.getParent() != null)
                                        ((LinearLayout) auto.getParent()).removeView(auto);
                                    auto.setPadding(40, 10, 40, 10);
                                    auto.setBackgroundColor(Color.parseColor("#fbfcfc"));
                                    auto.setWidth(lm.getWidth());

                                    ll.addView(auto);
                                } catch (JSONException e) {

                                }

                            }

                            @Override
                            public void onError(String errorMessage, int errorCode, @Nullable String dataObject) {
                                Log.d("no brand jsonobj", "jason");


                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    EditText edt = new EditText(this);

                    edt.setSingleLine();
                    edt.setPadding(40, 10, 40, 10);
                    edt.setBackgroundColor(Color.parseColor("#fbfcfc"));
                    map.put(id, "text");
                    edt.setId(id);
                    id = id + 1;
                    lm.addView(edt);

                }
            }
                    lm.addView(ll);
                }

        Button button = new Button(this);
        button.setText("Upload");
        button.setOnClickListener(handleOnClick(button));
        lm.addView(button);
    }

    View.OnClickListener handleOnClick(final Button button) {


        return new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {

                button.getShadowColor();
                button.setBackgroundColor(255);
                json_var =0;

                JSONObject dataReadyForSubmit =  Form_Submit();
                Log.d("Json Final Submit", String.valueOf(dataReadyForSubmit));
                if(dataReadyForSubmit!=null)
                submitData(dataReadyForSubmit);
                else
                    Log.d("Json Final Submit", "null");


            }
        };
    }



    public JSONObject Form_Submit() {
//         Log.d("Tag_map_key", String.valueOf(map));

        for (int key : map.keySet()) {

            if ((map.get(key)).equals("radio")) {


                RadioGroup radioButtonGroup = (RadioGroup) findViewById(key);

                try {
                    int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(radioButtonID);
                    String S = rb.getText().toString();

                    TextView tw = (TextView) findViewById((key - 1));
                    tw.setTextColor(Color.parseColor("#000000"));
                    try {

                        UploadQCData.put((tw.getText().toString()), S);
                    } catch (Exception e) {

                    }
                } catch (Exception e) {
                    TextView tw = (TextView) findViewById((key - 1));
                    tw.setTextColor(Color.parseColor("#ff0000"));
                    json_var = 1;

                }

            }
         if ((map.get(key)).equals("text")) {
                EditText edittext = (EditText) findViewById(key);
                try {
                    String S = edittext.getText().toString();
                    if (S.trim().length() == 0) {
                        TextView tw = (TextView) findViewById((key - 1));
                        tw.setTextColor(Color.parseColor("#ff0000"));
                        json_var = 1;

                    } else {
                        TextView tw = (TextView) findViewById((key - 1));
                        tw.setTextColor(Color.parseColor("#000000"));
                        try {
                            UploadQCData.put((tw.getText().toString()), S);
                        } catch (Exception e) {

                        }
                    }


                } catch (Exception e) {
                    TextView tw = (TextView) findViewById((key - 1));
                    tw.setTextColor(Color.parseColor("#ff0000"));
                    json_var = 1;

                }
            }

//         else if ((map.get(key)).equals("flag")) {
//                RadioGroup radioButtonGroup = (RadioGroup) findViewById(key);
//                try {
//                    int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
//                    RadioButton rb = (RadioButton) findViewById(radioButtonID);
//                    String S = rb.getText().toString();
//                    // Log.d("Radio Text", S);
//
//                    TextView tw = (TextView) findViewById((key - 1));
//                    tw.setTextColor(Color.parseColor("#000000"));
//                    try {
//                        UploadQCData.put((tw.getText().toString()), S);
//                    } catch (Exception e) {
//
//                    }
//
//                } catch (Exception e) {
//
//                    TextView tw = (TextView) findViewById((key - 1));
//                    tw.setTextColor(Color.parseColor("#ff0000"));
//                    json_var = 1;
//                }
//            }

//

           else if ((map.get(key)).equals("autocomplete"))
            {

                int d = (int) map_autocomplete.get(auto_var);
                Log.d("AUTO TEXT AGAIN", String.valueOf(d));
                auto_var = auto_var + 1;
                if (map_autocomplete.size() == (auto_var)) {
                    auto_var = 0;
                }


                try {
                    String S = "";
                    AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(key);
                    S = auto.getText().toString();


                    if (S.trim().length() == 0) {

                        TextView tw = (TextView) findViewById((d));
                        tw.setTextColor(Color.parseColor("#ff0000"));

                    } else {
                        TextView tw = (TextView) findViewById((d));
                        tw.setTextColor(Color.parseColor("#000000"));
                        try {
                            UploadQCData.put((tw.getText().toString()), S);
                        } catch (Exception e) {

                        }
                    }
                    // Log.d("Autocomplete", s_1);

                } catch (Exception e) {
                     Log.d("Error_auto_complete","error");
                    TextView tw = (TextView) findViewById((key - 1));
                    tw.setTextColor(Color.parseColor("#ff0000"));
                    json_var = 1;

                }
             }
//                else if ((map.get(key)).equals("date"))
//                {
//                    TextView tw = (TextView) findViewById((key - 1));
//                    tw.setTextColor(Color.parseColor("#000000"));
//                    String S = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//                    try {
//                        UploadQCData.put((tw.getText().toString()), S);
//                    } catch (Exception e) {
//
//                    }
//                }
//


        }
        //
            if (UploadQCData.length()!=0&&json_var!=1)
            {
            Log.d("Json Result", String.valueOf(UploadQCData));
            Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
            return UploadQCData;

            }
        else {
                Toast.makeText(this,"Incomplete Form",Toast.LENGTH_LONG).show();
                return null;
            }
    } //collects data from all editboxes and other boxes


    public void submitData(JSONObject dataReadyFoSubmit)
    {
        product_summary a=new product_summary();


        try {

            appProvider.submitData(dataReadyFoSubmit,new IViewCallback<JSONObject>()
            {
                @Override
                public void onSuccess(JSONObject dataObject) {
                    Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();
                    Log.d("Submit", String.valueOf(dataObject));
                    UpdateLStatus("Under ME");


                }

                @Override
                public void onError(String errorMessage, int errorCode, @Nullable JSONObject dataObject) {

                    Log.d("Submit","error in submissionat fetchcategory ");


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void UpdateLStatus(final String status)
    {

        appProvider.update_L_status(status,new IViewCallback<JSONObject>() {


            @Override
            public void onSuccess(JSONObject dataObject) {

                Toast.makeText(getApplicationContext(),"changed Lstatus",Toast.LENGTH_SHORT).show();
                Log.d("changedto","new");

            }


            @Override
            public void onError(String errorMessage, int errorCode, @Nullable JSONObject dataObject) {



            }


        });


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

}
