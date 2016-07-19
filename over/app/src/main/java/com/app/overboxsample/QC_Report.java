package com.app.overboxsample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.overboxsample.providers.AppProvider;

import org.json.JSONException;

import java.util.Iterator;

public class QC_Report extends AppCompatActivity implements View.OnClickListener {
    AppProvider appProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            super.onCreate(savedInstanceState);


            setContentView(R.layout.qc_report);

            appProvider = new AppProvider();
            TableLayout QCR = (TableLayout) findViewById(R.id.qc_report);
            QCR.setStretchAllColumns(true);


            Iterator<String> it = imei_check.productAllDetails.keys();
            while (it.hasNext()) {
                TableRow tr = new TableRow(this);
                TextView c1 = new TextView(this);
                String key = it.next();

                c1.setText(key);
                c1.setTextSize(18);
                c1.setTextColor(Color.parseColor("#357ed5"));

                TextView cp=new TextView(this);
                cp.setText("  ");

                TextView c2 = new TextView(this);
                c2.setText(imei_check.productAllDetails.getJSONObject(key).getString("value"));
                c2.setTextSize(18);
                tr.addView(c1);
                tr.addView(c2);

                QCR.addView(tr);

            }
        } catch (Exception e) {

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
            case R.id.OKButton:
                try {
                    if (imei_check.productOnUpload.get("lstatus").equals("Under QC")) {

                        Intent j = new Intent(QC_Report.this, Display_form.class);
                        startActivity(j);

                        break;
                    }
                    else {
                        Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}