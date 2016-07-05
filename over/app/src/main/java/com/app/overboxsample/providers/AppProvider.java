package com.app.overboxsample.providers;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.overboxsample.imei_check;
import com.app.overboxsample.product_summary;
import com.app.overboxsample.network.RequestFactory;
import com.app.overboxsample.network.interfaces.IViewCallback;
import com.app.overboxsample.network.request.Payload;
import com.app.overboxsample.network.request.Request;
import com.app.overboxsample.network.response.HttpResponse;
import com.app.overboxsample.network.response.HttpResponseStatus;
import com.app.overboxsample.network.utils.HttpMethod;
import com.app.overboxsample.network.volley.VolleyQueueUtils;
import com.app.overboxsample.network.volley.VolleyStringRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class AppProvider extends BaseProvider {

    public AppProvider() {

    }


    //managing request for login
    public void loginc( String id1, String id2, final IViewCallback<JSONObject> call)
    {
    Payload payload = new Payload();
    payload.add("user_name",id1);
    payload.add("user_pass",id2);

    Request request = RequestFactory.createRequest(
            HttpMethod.POST, "http://stage.overboxd.com/index.php/loginapi/", null, payload, null, 60000, null, null);

    VolleyQueueUtils.getGeneralRequestQueue().add(new VolleyStringRequest(request, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {

                HttpResponse<JSONObject> httpResponse = new HttpResponse<>(
                        new HttpResponseStatus(),
                        new JSONObject(response)
                );
                notifyResponse(httpResponse, call);
            }

            catch (JSONException e) {

            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {



        }
    }));
}


    //managing request for fetching Brand and colour
    public void fetchval(String url,final IViewCallback<String> call)
    {
        Payload payload = new Payload();
        Request request = RequestFactory.createRequest(
                HttpMethod.POST, url,null, payload, null, 60000, null, null);

        VolleyQueueUtils.getGeneralRequestQueue().add(new VolleyStringRequest(request, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    HttpResponse<String> httpResponse = new HttpResponse<>(
                            new HttpResponseStatus(),
                            response
                    );

                    notifyResponse(httpResponse, call);

                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));


    }


    //fetching and returning the data for the formation form
//    public void fetchCategories(final IViewCallback<JSONObject> callback) {
//        Payload payload = new Payload();
//        payload.add("key", "ajkT14Asdfe526fasdfJKCckecsdps");
//        payload.add("command", "fetch_categories");
//
//        Request request = RequestFactory.createRequest(
//          //      HttpMethod.POST, "http://stage.overboxd.com/index.php/generalqcapi", null, payload, null, 60000, null, null);
//                HttpMethod.POST, "http://stage.overboxd.com/index.php/generalqcapi", null, payload, null, 60000, null, null);
//
//
//        VolleyQueueUtils.getGeneralRequestQueue().add(new VolleyStringRequest(request, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//
//                    HttpResponse<JSONObject> httpResponse = new HttpResponse<>(
//                            new HttpResponseStatus(),
//                            new JSONObject(response)
//                    );
//
//                    notifyResponse(httpResponse, callback);
//
//                } catch (JSONException e) {
//                    Log.d("Tag","fetchcategory mein prob");
//
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }));
//    }

    public void fetchCategoryDetails(String categoryId, final IViewCallback<JSONObject> callback)
    {
        Payload payload = new Payload();
        payload.add("key", "ajkT14Asdfe526fasdfJKCckecsdps");
//        payload.add("command", "fetch_object");
//        payload.add("category_id", categoryId);



        Log.d("nitify1", "it");
        categoryId="16";
        Request request = RequestFactory.createRequest(
//             HttpMethod.POST, "http://stage.overboxd.com/index.php/generalqcapi"+categoryId, null, payload, null, 60000, null, null);
                HttpMethod.POST, "http://dev.api.overboxd.com/api/marketplace/generalqcapi/"+categoryId, null, payload, null, 60000, null, null);
                VolleyQueueUtils.getGeneralRequestQueue().add(new VolleyStringRequest(request, new Response.Listener<String>()
                 {
                        @Override
                        public void onResponse(String response) {
                                            HttpResponse<JSONObject> httpResponse = null;
                                            try {
                                                httpResponse = new HttpResponse<>(
                                                        new HttpResponseStatus(),
                                                        new JSONObject(response)
                                                );
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            Log.d("nitify2","it");
                                            notifyResponse(httpResponse, callback);
                             }
                 },new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {

                                                        }
                                                    }));

    }


    public void fetch_Imei(EditText edt,final IViewCallback<JSONObject> call)
    {


        JSONObject json=new JSONObject();

        String imeiInput=String.valueOf(edt.getText());
        imeiInput="gatch";

        try {
            json.put("imei",imeiInput);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("fetch_imei", String.valueOf(json));
        String URL="http://dev.api.overboxd.com/api/marketplace/imei";
       //RequestQueue rq= Volley.newRequestQueue();

        JsonObjectRequest req = new JsonObjectRequest
                (com.android.volley.Request.Method.POST,URL,json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        HttpResponse<JSONObject> httpResponse = new HttpResponse<>(new HttpResponseStatus(),response);
                        notifyResponse(httpResponse, call);
                        Log.d("imei show ","here");
                        //VolleyLog.v("Response:%n %s", response.toString(4));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error","no response from volley");

                VolleyLog.e("Error: ", error.getMessage());



            }
        });

        VolleyQueueUtils.getGeneralRequestQueue().add(req);



    }


    public void submitData(JSONObject val,final IViewCallback<JSONObject> call)
    {
        try{
        Log.d("submission", String.valueOf(val));
        JSONObject result=val;


            String buf=(String)result.get("Imei");
            result.remove("Imei");
            result.put("imei",buf );

        String URL="http://dev.api.overboxd.com/api/marketplace/submit";

        JsonObjectRequest req = new JsonObjectRequest(com.android.volley.Request.Method.POST,URL,result,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        HttpResponse<JSONObject> httpResponse = new HttpResponse<>(new HttpResponseStatus(),response);
                        notifyResponse(httpResponse, call);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error", "no response from volley");

                VolleyLog.e("Error: ", error.getMessage());

            }
        });




        VolleyQueueUtils.getGeneralRequestQueue().add(req);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public void getInitialImei(EditText val,final IViewCallback<JSONObject> call)
    {
        try{
            Log.d("submission", String.valueOf(val));
            //JSONObject result=val
            String textValue = String.valueOf(imei_check.imeival.getText());



//            String buf=(String)result.get("Imei");
//            result.remove("Imei");
//            result.put("imei",buf );

            textValue="358995057154816";
            String URL="http://dev.api.overboxd.com/api/marketplace/product/search/imei/"+textValue;

//            JsonObjectRequest req = new JsonObjectRequest(com.android.volley.Request.Method.GET,URL,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            HttpResponse<JSONObject> httpResponse = new HttpResponse<>(new HttpResponseStatus(),response);
//                            notifyResponse(httpResponse, call);
//
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                    Log.d("Error", "no response from volley");
//
//                    VolleyLog.e("Error: ", error.getMessage());
//
//                }
//            });


            Payload payload=new Payload();
            Request request = RequestFactory.createRequest(
                    HttpMethod.GET,URL, null, payload, null, 60000, null, null);
            VolleyQueueUtils.getGeneralRequestQueue().add(new VolleyStringRequest(request, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    HttpResponse<JSONObject> httpResponse = null;
                    try {
                        httpResponse = new HttpResponse<>(
                                new HttpResponseStatus(),
                                new JSONObject(response)
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("nitify2","it");
                    notifyResponse(httpResponse, call);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }







}
