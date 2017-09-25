package com.studentinformationproj.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Manisha on 9/24/2017.
 * This class is used for get and post method used for server communication
 * through Volley
 */

public class ServerConnection {

    Context serverContext;

     public ServerConnection(Context context){
        this.serverContext = context;
    }

    //This Method is for post
    public void PostServerCommunication(Context ServerContext, String ServerRequestUrl, final Map<String,String> MapString , final PostCommentResponseListener CallBack){
        RequestQueue queue = Volley.newRequestQueue(ServerContext);
        final ProgressDialog dialog;
        dialog = ProgressDialog.show(ServerContext, "Server Response" , "Uploading....", true);
        StringRequest sr = new StringRequest(Request.Method.POST, ServerRequestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                CallBack.requestCompleted(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                CallBack.requestEndedWithError(error);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params = MapString ;
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        //Set the default value like timeout 180000 milliseconds timeout, 0 mean number of retry
        sr.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        sr.setShouldCache(false);
        queue.add(sr);

    }

    public interface PostCommentResponseListener {
        public void requestStarted();
        public void requestCompleted(String data);
        public void requestEndedWithError(VolleyError error);
    }
    //This Method is for Get
    public void GetServerCommunication(Context getServerContext, String getServerRequestUrl,final GetCommentResponseListener getCallBack){
        RequestQueue getQueue = Volley.newRequestQueue(getServerContext);
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(getServerContext,"Server Response" , "Loading....", true);
        StringRequest sr = new StringRequest(Request.Method.GET, getServerRequestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                getCallBack.getRequestCompleted(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                getCallBack.getRequestEndedWithError(error);
            }
        });
        //Set the default value like timeout 180000 milliseconds timeout, 0 mean number of retry
        sr.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        sr.setShouldCache(false);
        getQueue.add(sr);


    }
    public interface GetCommentResponseListener {
        public void getRequestStarted();
        public void getRequestCompleted(String data);
        public void getRequestEndedWithError(VolleyError error);
    }
}
