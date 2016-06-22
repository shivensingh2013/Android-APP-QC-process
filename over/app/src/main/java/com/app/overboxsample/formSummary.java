//package com.app.overboxsample;
//
///**
// * Created by shivendra on 6/22/16.
// */
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.AbsListView;
//import android.widget.EditText;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.app.overboxsample.network.interfaces.IViewCallback;
//import com.app.overboxsample.providers.AppProvider;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.w3c.dom.Text;
//
//import java.util.Iterator;
//
//public class formSummary extends AppCompatActivity implements View.OnClickListener
//{
//
//
//    AppProvider appProvider;
//
//    @Override
//
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.table_layout);
//
//
//        formSummary(imei_check.jsonresult);
//        appProvider = new AppProvider();
//
//
//
//    }
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        appProvider.attach();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        appProvider.detach();
//    }
//
//    @Override
//    public void onClick(View view)
//    {
//
//        switch (view.getId()) {
//            case R.id.button:
//                formSubmit();
//                break;
//        }
//
//    }
//
//    protected void formSummary(JSONObject result)
//    {
//        TableLayout tl=(TableLayout) findViewById(R.id.table_layout);
//        Iterator<String> it=result.keys();
//
//
//
//        while(it.hasNext())
//        {
//            TableRow tr=new TableRow(this);
//         //   tr.setLayoutParams(new AbsListView.LayoutParams( TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
//
//
//            TextView g=new TextView(this);
//            String ja= null ;
//
//
//            try {
//            ja = (String)result.get(buf);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//            g.setText(ja);
//
//
//
//            tr.addView(t);
//            tr.addView(g);
//
//
//            tl.addView(tr);
//
//
//        }
//
//
//    }
//
//    protected void formSubmit()
//    {
//
//    }
//
//}