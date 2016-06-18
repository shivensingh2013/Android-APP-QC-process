package com.app.overboxsample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.app.overboxsample.providers.AppProvider;

/**
 * Created by shivendra on 5/23/2016.
 */

public class option_imei extends AppCompatActivity implements View.OnClickListener {


    AppProvider appProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_lay);
        Button bt1 = (Button) findViewById(R.id.NEW);
        Button bt2 = (Button) findViewById(R.id.OLD);

        appProvider = new AppProvider();


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
    public void onClick(View v) {


        Intent i;
        switch (v.getId()) {
            case R.id.NEW:

                v.findViewById(R.id.NEW).setBackgroundColor(Color.parseColor("#fbc2c2c2"));

                 i = new Intent(option_imei.this, fetch_category.class);

                startActivity(i);


                break;
            case R.id.OLD:

                v.findViewById(R.id.OLD).setBackgroundColor(Color.parseColor("#fbc2c2c2"));
                 i = new Intent(option_imei.this, imei_check.class);

                startActivity(i);



                break;

        }
    }
}

