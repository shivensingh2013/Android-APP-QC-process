package com.app.overboxsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.overboxsample.network.interfaces.IViewCallback;
import com.app.overboxsample.providers.AppProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by shivendra on 5/23/2016.
 */

public class login extends AppCompatActivity implements View.OnClickListener{

    String id1;
    String id2;


    SwipeRefreshLayout swipeRefreshLayout;
    List category_id_array;
    public static List[] object;
    AppProvider appProvider;
    ListView listview;
    public  static  int  category_id;
    ArrayAdapter<String> adapter;
    EditText id;
    EditText id_pass;

    ImageView image;
@Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_lay);
    image = (ImageView) findViewById(R.id.imageView);

    image.setImageResource(R.mipmap.overcart);
        id=(EditText) findViewById(R.id.editText);
       id_pass=(EditText) findViewById(R.id.editText2);

        appProvider = new AppProvider();
        Toast.makeText(getApplicationContext(),"not ing",Toast.LENGTH_SHORT).show();


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
    public void onClick(View view)
    {

        switch (view.getId()) {
            case R.id.Login:
                Login();
                break;
    }


    }

    public void Login()


        {


            if (id != null) {
                id1= String.valueOf(id.getText());
                Toast.makeText(getApplicationContext(),id1,Toast.LENGTH_SHORT).show();
            }
            if(id_pass!=null)
            {
                id2=String.valueOf(id_pass.getText());
                Toast.makeText(getApplicationContext(),id2,Toast.LENGTH_SHORT).show();
            }


            appProvider.loginc(id1,id2,new IViewCallback<JSONObject>()
            {


                @Override
                public void onSuccess(JSONObject dataObject)
                {

//                    TextView a=(TextView) findViewById(R.id.error_print);
//                    a.setText(" ");
                    String val= null;
                    try {

                        val = dataObject.getString("data");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if(val.equals("true"))
                    {
                        Log.d("no error",val);

                        Intent i = new Intent(login.this, LauncherActivity.class);

                        startActivity(i);


                    }
                    else
                    {

                    }


                }




                @Override
                public void onError(String errorMessage, int errorCode, @Nullable JSONObject dataObject) {
                    Log.d("onerror","jason");
                    Toast.makeText(getApplicationContext(),"error no success",Toast.LENGTH_SHORT).show();

                }


            });

         }
}
