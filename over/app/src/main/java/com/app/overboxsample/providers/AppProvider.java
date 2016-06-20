package com.app.overboxsample.providers;

import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
    Log.d("payload run",id1);
    Payload payload = new Payload();
    payload.add("user_name",id1);
    payload.add("user_pass",id2);

    Request request = RequestFactory.createRequest(
            HttpMethod.POST, "http://stage.overboxd.com/index.php/loginapi/", null, payload, null, 60000, null, null);

    if(request==null)
    {
        Log.d("request not going","goo na");
    }
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
                //Toast.makeText(context,"Msg", Toast.LENGTH_LONG).show();

            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }));
}


    //managing request for fetching the category
    public void fetchval(String url,final IViewCallback<String> call)
    {
        Payload payload = new Payload();
//        payload.add("key", "ajkT14Asdfe526fasdfJKCckecsdps");
//        payload.add("command", "fetch_categories");

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
                    Log.d("Tag","fetchval mein prob");


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));


    }


    //fetching and returning the data for the formation form
    public void fetchCategories(final IViewCallback<JSONObject> callback) {
        Payload payload = new Payload();
        payload.add("key", "ajkT14Asdfe526fasdfJKCckecsdps");
        payload.add("command", "fetch_categories");

        Request request = RequestFactory.createRequest(
                HttpMethod.POST, "http://stage.overboxd.com/index.php/generalqcapi", null, payload, null, 60000, null, null);

        VolleyQueueUtils.getGeneralRequestQueue().add(new VolleyStringRequest(request, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    HttpResponse<JSONObject> httpResponse = new HttpResponse<>(
                            new HttpResponseStatus(),
                            new JSONObject(response)
                    );

                    notifyResponse(httpResponse, callback);

                } catch (JSONException e) {
                    Log.d("Tag","fetchcategory mein prob");


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }

    public void fetchCategoryDetails(String categoryId, final IViewCallback<String> callback) {
        Payload payload = new Payload();
        payload.add("key", "ajkT14Asdfe526fasdfJKCckecsdps");
        payload.add("command", "fetch_object");
        payload.add("category_id", categoryId);



//        Request request = RequestFactory.createRequest(
//                HttpMethod.POST, "http://www.overboxd.com/index.php/generalqcapi", null, payload, null, 60000, null, null);
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
//                } catch (JSONException e) {
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }));
        Log.d("nitify1","it");
        Request request = RequestFactory.createRequest(
                HttpMethod.POST, "http://stage.overboxd.com/index.php/generalqcapi", null, payload, null, 60000, null, null);

        VolleyQueueUtils.getGeneralRequestQueue().add(new VolleyStringRequest(request, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                HttpResponse<String> httpResponse = new HttpResponse<>(
                        new HttpResponseStatus(),
                        response
                );
                Log.d("nitify2","it");
                notifyResponse(httpResponse, callback);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }


    public void fetch_Imei(JSONObject val,EditText edt,final IViewCallback<JSONObject> call){


        JSONObject json=new JSONObject();

        String imeiInput=String.valueOf(edt.getText());

        try {
            json.put("imei",imeiInput);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("fetch_imei",String.valueOf(json));


        String URL="http://dev.api.overboxd.com/api/marketplace/imei";
       // RequestQueue rq= Volley.newRequestQueue();
        CustomPriorityRequest req = new CustomPriorityRequest
                (com.android.volley.Request.Method.POST,URL,json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        HttpResponse<JSONObject> httpResponse = new HttpResponse<>(new HttpResponseStatus(),response);
                        notifyResponse(httpResponse, call);


                        Log.d("imei show ", "here");
                        //VolleyLog.v("Response:%n %s", response.toString(4));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error","no response from volley");

                VolleyLog.e("Error: ", error.getMessage());



            }
        });




        req.setPriority(com.android.volley.Request.Priority.IMMEDIATE);



        VolleyQueueUtils.getGeneralRequestQueue().add(req);





    }


    public void submitData(JSONObject val,final IViewCallback<JSONObject> call)
    {
        Log.d("submission",String.valueOf(val));


        String URL="http://dev.api.overboxd.com/api/marketplace/submit";

        JsonObjectRequest req = new JsonObjectRequest(com.android.volley.Request.Method.POST,URL,val,
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









}
