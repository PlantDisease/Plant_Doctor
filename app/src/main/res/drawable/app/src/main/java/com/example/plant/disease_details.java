package com.example.plant;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class disease_details extends AppCompatActivity {
    public String Disease_Name,Description,Reason,Methods;
    private static final String ROOT_URL = "https://savewithblood.000webhostapp.com/getInfo.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);
        Bundle p=getIntent().getExtras();
//        Disease_Name=p.getString("disease");
//        String[] parts= Disease_Name.split("__"); //returns an array with the 2 parts
//        Disease_Name = parts[1];
        Log.d("disease_name",p.getString("disease"));
       final TextView disease_Name=(TextView) findViewById(R.id.dname);
       final TextView description=(TextView) findViewById(R.id.description);
       final TextView reason=(TextView) findViewById(R.id.reason);
       final TextView methods=(TextView)findViewById(R.id.methods);
       final ImageView image=(ImageView)findViewById(R.id.disease_image);
        disease_Name.setText(Disease_Name);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonResponse;
                        try
                        {
                            jsonResponse = new JSONObject(response);
                            Disease_Name=jsonResponse.getString("name");
                            disease_Name.setText(Disease_Name);
                            String base=jsonResponse.getString("image");
                            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
                            image.setImageBitmap(
                                    BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                            );
                            Description=jsonResponse.getString("description");
                            description.setText(Description);
                            Reason =jsonResponse.getString("reason");
                            reason.setText(Reason);
                            Methods=jsonResponse.getString("methods");
                            methods.setText(Methods);
                            Toast.makeText(disease_details.this,Disease_Name,Toast.LENGTH_LONG).show();
                        }
                        catch (JSONException e)
                        {
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("disease", "Apple Scab");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
