package com.example.plant;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Forecast extends Crop_Cultivation {
    private static final String PredictionUrl = "http://eead3423a309.ngrok.io";
    public String predicted="";
    public int SOCKET_TIMEOUT = 30000;
    Context context;

    public void Forecast(final String district_name, final String crop_name ,final Context context) {
        this.context=context;
        final TextView production=(TextView) ((Activity)context).findViewById(R.id.result);
        final TextView calculated=(TextView) ((Activity)context).findViewById(R.id.Calculated);
        final EditText acre = (EditText) ((Activity)context).findViewById(R.id.acre);
        final Button calculateButton = (Button) ((Activity)context).findViewById(R.id.calculate);
        final TextView textView = (TextView) ((Activity)context).findViewById(R.id.textView12);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, PredictionUrl + "/getProduction?district="+district_name+"&crop="+crop_name,
                new Response.Listener<String>() {
                    @Override
                    synchronized   public void onResponse(String response) {
                        JSONObject jsonResponse;
                        try {
                            jsonResponse = new JSONObject(response);
                            predicted = jsonResponse.getString("prediction");
                            predicted=predicted.replaceAll("\\[", "").replaceAll("\\]","");
                            float res=Float.parseFloat(predicted);

                            res=Math.round(res);
                            production.setText(String.valueOf(res)+" Quintal per Acre");
                            textView.setVisibility(View.VISIBLE);
                            acre.setVisibility(View.VISIBLE);
                            calculateButton.setVisibility(View.VISIBLE);
                            final float finalRes = res;
                            calculateButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    float useracre= (float) Float.parseFloat(acre.getText().toString());
                                    calculated.setVisibility(View.VISIBLE);
                                    float result=useracre* finalRes;
                                    calculated.setText(String.valueOf(result)+" Quintal");
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  loading.dismiss();
                        error.printStackTrace();
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
