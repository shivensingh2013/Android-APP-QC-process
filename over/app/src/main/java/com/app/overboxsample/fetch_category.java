package com.app.overboxsample;

import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;

public class fetch_category extends AppCompatActivity {



   public static List lt;
    int ed_p =0;
    int radio_p =0;
    AutoCompleteTextView auto;
    ListView list;
    LinearLayout lm;

HashMap<String,Integer> map = new HashMap<String,Integer>();


    List<EditText> allEds = new ArrayList<EditText>();

    List<CheckBox> allchecks = new ArrayList<CheckBox>();

    List<RadioGroup> allradio  = new ArrayList<RadioGroup>();


    AppProvider appProvider;
    ArrayAdapter<String> adapter1;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.fetch_category);

        appProvider = new AppProvider();

        ButterKnife.inject(this);

      //  categoryJsonDump = (TextView)findViewById(R.id.textView);

       List values =  LauncherActivity.object[0];
        List keys =  LauncherActivity.object[1];


        // Creating a new RelativeLayout
//        ScrollView scrollView = new ScrollView(this);

         lm = (LinearLayout) findViewById(R.id.linearMain);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        for(int i=0;i<keys.size();i++)
        {
            String s = (String) keys.get(i);
            String value  = (String) values.get(i);

            //create a dynamic layout
            final LinearLayout ll = new LinearLayout(this);
            TextView product = new TextView(this);
            product.setText(s + "    ");
            product.setTextSize(20);

          product.setPadding(0,10,0,10);
           // product.setPadding(40,10,40,10);


          //  product.setTextColor(65281);
            product.setGravity(View.TEXT_ALIGNMENT_CENTER);
            lm.addView(product);

            map.put(value,i);               //save the value and its corressponding ID

            if(value.equals("flag"))
            {
               //enter
                final  RadioButton[] rb = new RadioButton[2];
                RadioGroup rg = new RadioGroup(this); //create the RadioGroup
                //allradio.add(rg);
//                rg.setId(radio_p);
//                radio_p = radio_p +1;
                rg.setOrientation(RadioGroup.VERTICAL);
                rb[0]  = new RadioButton(this);
                rb[1]  = new RadioButton(this);
                rg.addView(rb[0]); //the RadioButtons are added to the radioGroup instead of the layout

                rb[0].setText("complete");
                rg.addView(rb[1]);
                rb[1].setText("incomplete");
                if(rg.getParent()!=null)
                    ((LinearLayout)rg.getParent()).removeView(rg);
                rg.setPadding(40, 10, 40, 10);
                lm.addView(rg);

            }

            else if(value.equals("text"))
            {
                EditText edt = new EditText(this);
                edt.setSingleLine();
//                edt.getBackgroundTintMode();
                edt.setPadding(70, 10, 70, 10);
//                allEds.add(edt);
//                edt.setId(ed_p);
//                ed_p = ed_p +1;
                lm.addView(edt);
            }
            else if(value.equals("date"))
            {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                TextView date1 = new TextView(this);
                date1.setText(date + "    ");
                date1.setPadding(70, 10, 70, 10);
                lm.addView(date1);

            }
            //if radio in value we have to make it a object
            else if(value.contains("radio"))
                {

                    JSONObject jsonObj = null;
                    String r = null;
                    try {
                         jsonObj = new JSONObject(value);
                         r = jsonObj.getString("radio");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray array = null;
                    try {
                         array = new JSONArray(r);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                   final  RadioButton[] rb = new RadioButton[array.length()];
                    RadioGroup rg = new RadioGroup(this); //create the RadioGroup
//                    allradio.add(rg);
//                    rg.setId(ed_p);
//                    ed_p = ed_p +1;
                    rg.setOrientation(RadioGroup.VERTICAL);


                   for(int j =0;j< array.length();j++ ) {
//
                      try {
//
                           rb[j]  = new RadioButton(this);
                           rg.addView(rb[j]); //the RadioButtons are added to the radioGroup instead of the layout

                          rb[j].setText(String.valueOf(array.get(j)) + "    ");

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   if(rg.getParent()!=null)
                       ((LinearLayout)rg.getParent()).removeView(rg);
                       rg.setPadding(70, 10, 70, 10);
                       lm.addView(rg);

                   }
                }

            else if(value.contains("checkbox"))
            {

                JSONObject jsonObj = null;
                String r = null;
                try
                {
                    jsonObj = new JSONObject(value);
                    r = jsonObj.getString("checkbox");
                    jsonObj = new JSONObject(r);
                    Iterator<String> it = jsonObj.keys();

                    while (it.hasNext()) {
                        String key = it.next();
                        CheckBox check = new CheckBox(this);
//                        allchecks.add(check);
//                        check.setText(key + "    ");
//                       check.setId(ed_p);
                        ed_p = ed_p +1;
                        check.setPadding(70, 10, 70, 10);
                        lm.addView(check);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    }


            }

            else if(value.contains("autocomplete")) {


                JSONObject obj = null;
                try {
                    obj = new JSONObject(value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String ap = "";
                try {
                    ap = obj.getString("url");


                    if (ap.contains("Brand"))
                        ap = ap + "?brand=";
                    else
                        ap = ap + "?color=";

                    appProvider.fetchval(ap, new IViewCallback<String>() {
                        @Override
                        public void onSuccess(String dataObject) {
                            JSONArray a = null;

                            try {
                                a = new JSONArray(dataObject);
                                //   List<String> start = new ArrayList<String>();
                                //we are creating a temporary spinner named variable .After adding this to the ll layout the name of spinner is forgoteten and so can be used again.
                                auto = (AutoCompleteTextView) new AutoCompleteTextView(fetch_category.this);
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
                                auto.setPadding(70,10,70,10);
                                ll.addView(auto);
                            } catch (JSONException e) {
                                //   Log.d("error fetchval", "error");
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

            }
                    else
                    {
                        EditText edt = new EditText(this);
                        allEds.add(edt);
                        edt.setSingleLine();

                        edt.setPadding(70,10,70,10);
                        lm.addView(edt);
                        edt.setId(ed_p);
                        ed_p = ed_p+1;
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

              //  button.getShadowColor();

              //  button.scrollTo(View.FOCUS_DOWN,View.FOCUS_DOWN);
                submitData();


              //  I will start reviewing my data form similar to tat of update form and option for updating it and this will go in a loop

           //     formisValid();


            }
        };
    }

    public void submitData()
    {

        try {
            final JSONObject formData=new JSONObject();
            formData.put("imei", "default");
            appProvider.submitData(formData, new IViewCallback<JSONObject>()
            {
                @Override
                public void onSuccess(JSONObject dataObject) {

                    try {
                        Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();
                        if(dataObject.has("data")) {

                        dataObject.remove("data");
                            dataObject.put("data",String.valueOf(formData));
                        }



                        Log.d("Submit",String.valueOf(dataObject));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Data Not Saved",Toast.LENGTH_SHORT).show();
                        //reaches fetch category form again with errors
                        e.printStackTrace();
                    }
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

    public void formisValid()
    {


        for (String key : map.keySet()) {
            Log.d("Map Key Value", String.valueOf(map.get(key)));

        }

//        String[] strings = new String[ed_p];
//        for(int i=0; i < allEds.size(); i++){
//        strings[i] = allEds.get(i).getText().toString();
//        Log.d("Edit Text String", String.valueOf(strings[i]));
//              }
//
//        for(int i=0; i < allradio.size(); i++)
//        {
//            int radioButtonID = (allradio.get(i)).getCheckedRadioButtonId();
//            RadioButton radioButton = (RadioButton) findViewById(radioButtonID);
//            try {
//
//
//                    if (radioButton != null) {
//                        Log.d("Radio Text String", String.valueOf(radioButton.getText()));
//                    }
//                }
//                catch(Exception e)
//                {
//
//                }
//
//        }


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
