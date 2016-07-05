package com.app.overboxsample;

import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

public class Display_form extends AppCompatActivity {



    public static List lt;

    JSONObject jsonresult;

    int id = 0;
    int json_var = 0;
    int auto_var = 0;   //used to map TextView with AutoTextComplete
    JSONObject jb;
    AutoCompleteTextView auto;

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
        jsonresult = new JSONObject();

        appProvider = new AppProvider();

        ButterKnife.inject(this);

        //fetching the category value



        List values =  imei_check.formDisplayingObject[0];
        List keys =  imei_check.formDisplayingObject[1];

        JSONObject jsonObject =imei_check.productAllDetails;





        try {
            JSONArray value = jsonObject.getJSONArray("imeis");

           jb = (JSONObject)value.getJSONObject(0);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        lm = (LinearLayout) findViewById(R.id.linearMain);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        for(int i=0;i<keys.size();i++)
        {
            String s = (String) keys.get(i);
            String value  = (String) values.get(i);
             map_key.put(id, s);
            String value1 = null;
            try {
                value1 = jb.getString(s);
             //   Log.d("Keys Value", String.valueOf(value1));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final LinearLayout ll = new LinearLayout(this);
            TextView product = new TextView(this);
            product.setId(id);

            product.setText(s+"");
            product.setTextSize(20);
            product.setPadding(0, 10, 0, 10);
            product.setTextColor(Color.parseColor("#0e6655"));

            product.setGravity(View.TEXT_ALIGNMENT_CENTER);
            lm.addView(product);

            if(value.contains("autocomplete"))
            {
//
                map_autocomplete.add(id);
            }
            id = id+1;



            if(value.equals("flag"))
            {
                int default_id = 0;
                //enter
                map.put(id,value);
                final  RadioButton[] rb = new RadioButton[2];
                RadioGroup rg = new RadioGroup(this); //create the RadioGroup

                //allradio.add(rg);
                rg.setId(id);
                id =id+ 1;

                rg.setOrientation(RadioGroup.VERTICAL);

                rb[0]  = new RadioButton(this);
                rb[0].setId(id);
                if(value1.equals("complete"))
                {
                    default_id = id;
                }
                id = id+1;
                rb[1]  = new RadioButton(this);
                rb[1].setId(id);
                if(value1.equals("incomplete"))
                {
                     default_id = id;
                }
                id = id+1;
                rg.addView(rb[0]); //the RadioButtons are added to the radioGroup instead of the layout

                rb[0].setText("complete");
                rg.addView(rb[1]);
                rb[1].setText("incomplete");
                rg.check(default_id);
                if(rg.getParent()!=null)
                    ((LinearLayout)rg.getParent()).removeView(rg);
                rg.setPadding(40, 10, 40, 10);
                rg.setBackgroundColor(Color.parseColor("#fbfcfc"));
                lm.addView(rg);

            }

            else if(value.equals("text"))
            {
                map.put(id, value);
                EditText edt = new EditText(this);
                edt.setId(id);
                id =id+ 1;
//                edt.getBackgroundTintMode();
                edt.setPadding(40, 10, 40, 10);
                edt.setBackgroundColor(Color.parseColor("#fbfcfc"));
                edt.setText(value1);

                lm.addView(edt);
            }
            else if(value.equals("date"))
            {
                map.put(id,value);
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                id =id+ 1;
                TextView date1 = new TextView(this);
                date1.setText(date);
                date1.setTextSize(20);
                date1.setTextColor(Color.parseColor("#2980b9"));
                date1.setPadding(40, 10, 40, 10);
                date1.setBackgroundColor(Color.parseColor("#fbfcfc"));
                lm.addView(date1);

            }
            //if radio in value we have to make it a object
            else if(value.contains("radio"))
            {
                map.put(id,"radio");

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
                rg.setId(id);
                id =id+ 1;


                rg.setOrientation(RadioGroup.VERTICAL);


                for(int j =0;j< array.length();j++ ) {

                    try {

                        rb[j]  = new RadioButton(this);
                        rb[j].setId(id);

                        rg.addView(rb[j]); //the RadioButtons are added to the radioGroup instead of the layout

                        rb[j].setText(String.valueOf(array.get(j)));
                        if(value1.equals(String.valueOf(array.get(j))))
                        {
                            Log.d("Values are equal",value1);
                            rb[j].setChecked(true);

                        }
                        id = id+1;


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                  //  rg.check(default_id);
                    if(rg.getParent()!=null)
                        ((LinearLayout)rg.getParent()).removeView(rg);
                    rg.setPadding(40, 10, 40, 10);
                    rg.setBackgroundColor(Color.parseColor("#fbfcfc"));

                    lm.addView(rg);

                }

                Log.d("Radio array", String.valueOf(array));
                Log.d("compare value",value1);
                Log.d("End", "################");
            }

            else if(value.contains("checkbox"))
            {
                map.put(id,"checkbox");
                id =id+ 1;

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
//

                        check.setPadding(40, 10, 40, 10);
                        check.setBackgroundColor(Color.parseColor("#fbfcfc"));
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

                    final String finalValue = value1;
                    appProvider.fetchval(ap, new IViewCallback<String>() {
                        @Override
                        public void onSuccess(String dataObject) {
                            JSONArray a = null;

                            try {
                                a = new JSONArray(dataObject);
                                //   List<String> start = new ArrayList<String>();
                                //we are creating a temporary spinner named variable .After adding this to the ll layout the name of spinner is forgoteten and so can be used again.
                                map.put(id,"autocomplete");
                                auto = (AutoCompleteTextView) new AutoCompleteTextView(Display_form.this);
                                auto.setId(id);
                                auto.setText(finalValue);
                                id = id+1;
                                JSONObject jb = null;
                                lt = new ArrayList();
                                for (int j = 0; j < a.length(); j++) {
                                    jb = (JSONObject) a.getJSONObject(j);
                                    lt.add(String.valueOf(jb.get("name")));

                                }

                                adapter1 = new ArrayAdapter<String>(Display_form.this
                                        , R.layout.support_simple_spinner_dropdown_item, lt);
                                adapter1.setDropDownViewResource
                                        (android.R.layout.simple_spinner_dropdown_item);

                                auto.setAdapter(adapter1);
                                auto.setThreshold(1);
                                auto.setDropDownWidth(500);
                                if (auto.getParent() != null)
                                    ((LinearLayout) auto.getParent()).removeView(auto);
                                auto.setPadding(40,10,40,10);
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

            }
            else
            {
                EditText edt = new EditText(this);

                edt.setSingleLine();
                edt.setPadding(40, 10, 40, 10);
                edt.setBackgroundColor(Color.parseColor("#fbfcfc"));
                map.put(id, "text");
                edt.setId(id);
                edt.setText(value1);
                id =id+ 1;
                lm.addView(edt);

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

                JSONObject j;
                j =  Form_Submit();

               submitData(j);



            }
        };
    }



    public JSONObject Form_Submit() {

        for (int key : map.keySet()) {

//
            if ((map.get(key)).equals("radio")) {


                RadioGroup radioButtonGroup = (RadioGroup) findViewById(key);
                //     radioButtonGroup.setBackgroundResource(R.color.colorPrimary);
                try {
                    int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(radioButtonID);
                    String S = rb.getText().toString();



                    TextView tw = (TextView) findViewById((key - 1));
                    tw.setTextColor(Color.parseColor("#000000"));
                    try {

                        jsonresult.put((tw.getText().toString()), S);
                    } catch (Exception e) {

                    }
                } catch (Exception e) {
                    TextView tw = (TextView) findViewById((key - 1));
                    tw.setTextColor(Color.parseColor("#ff0000"));
                    json_var = 1;

                }

            } else if ((map.get(key)).equals("text")) {
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
                            jsonresult.put((tw.getText().toString()), S);
                        } catch (Exception e) {

                        }
                    }


                } catch (Exception e) {
                    TextView tw = (TextView) findViewById((key - 1));
                    tw.setTextColor(Color.parseColor("#ff0000"));
                    json_var = 1;

                }
            } else if ((map.get(key)).equals("flag")) {
                RadioGroup radioButtonGroup = (RadioGroup) findViewById(key);
                try {
                    int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(radioButtonID);
                    String S = rb.getText().toString();

                    TextView tw = (TextView) findViewById((key - 1));
                    tw.setTextColor(Color.parseColor("#000000"));
                    try {
                        jsonresult.put((tw.getText().toString()), S);
                    } catch (Exception e) {

                    }

                } catch (Exception e) {

                    TextView tw = (TextView) findViewById((key - 1));
                    tw.setTextColor(Color.parseColor("#ff0000"));
                    json_var = 1;
                }
            }

//

            else if ((map.get(key)).equals("autocomplete")) {

                int d = (int) map_autocomplete.get(auto_var);

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
                            jsonresult.put((tw.getText().toString()), S);
                        } catch (Exception e) {

                        }
                    }


                } catch (Exception e) {

                    TextView tw = (TextView) findViewById((key - 1));
                    tw.setTextColor(Color.parseColor("#ff0000"));
                    json_var = 1;

                }
            }


        }

        if (jsonresult.length() != 0 && json_var == 0) {
            Log.d("Json Result", String.valueOf(jsonresult));
            Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
            return jsonresult;

        }
        return null;
    }





    public void submitData(JSONObject formData)
    {

        try {


            appProvider.submitData(formData, new IViewCallback<JSONObject>()
            {
                @Override
                public void onSuccess(JSONObject dataObject) {

                    Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();

                    Log.d("Submit",String.valueOf(dataObject));

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
