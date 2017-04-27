package com.dealfaro.luca.androidhomephone;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "androidhomephone";

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // We need the "final" keyword here to guarantee that the
        // value does not change, as it is used in the callbacks.

        //getList();
        //sendMsg("Anna", "Clara", "Tschuss!");
        //getMessages("abracadabra");
    }

    public void postMessage(View v) {
        final TextView my_textView = (TextView) findViewById(R.id.my_text);
        String post_url = "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_post";
        StringRequest sr = new StringRequest(Request.Method.POST,
                post_url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonBody = new JSONObject(response);
                    my_textView.setText(jsonBody.getString("result"));
                } catch (JSONException e){
                    throw new RuntimeException(e);
                }
                // JANKY WAY
                // my_textView.setText(response.substring(12,response.length()-2));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("token", "abracadabra");
                //my_textView.setText(params.get("token"));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);

    }

    public void getMessage(View v) {
        final TextView my_textView = (TextView) findViewById(R.id.my_text);

        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_get";

        String get_url = url + "?token=" + "abracadabra";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, get_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            my_textView.setText(response.getString("result"));
                        } catch (JSONException e) {
                            //my_textView.setText("Received bad json: " + e.getStackTrace());
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(LOG_TAG, error.toString());
                    }
                });

        queue.add(jsObjRequest);

    }

}
