package com.example.plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Crop_Cultivation extends AppCompatActivity {

    public String state_name, district_name, crop_name;
    private static final String URL = "https://savewithblood.000webhostapp.com/getstateInfo.php";
    public String[] district_array, crop_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop__cultivation);

        Spinner state = (Spinner)findViewById(R.id.state);
        final TextView production = (TextView)findViewById(R.id.result);
        final Spinner district = (Spinner)findViewById(R.id.district);
        final Spinner crop = (Spinner)findViewById(R.id.crop);
        EditText acre = (EditText)findViewById(R.id.acre);
        Button calculateButton = (Button) findViewById(R.id.calculate);
        TextView calculated = (TextView) findViewById(R.id.Calculated);
        calculated.setVisibility(View.INVISIBLE);
        acre.setVisibility(View.INVISIBLE);
        calculateButton.setVisibility(View.INVISIBLE);
        TextView textView = (TextView)findViewById(R.id.textView12);
        textView.setVisibility(View.INVISIBLE);

        ArrayAdapter<CharSequence> stateAdapter = new ArrayAdapter<CharSequence>(Crop_Cultivation.this, android.R.layout.simple_spinner_dropdown_item, CountryData.StateNames);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                state_name = parent.getItemAtPosition(position).toString();
                Log.d("State", state_name);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonresponse;
                        try {
                            jsonresponse = new JSONObject(response);
                            district_name = jsonresponse.getString("district");
                            district_array = district_name.split(",");
                            crop_name = jsonresponse.getString("crops");
                            crop_array = crop_name.split(",");

//                        Log.d("Name Received",jsonresponse.getString("name"));
                            ArrayAdapter<CharSequence> districtAdapter = new ArrayAdapter<CharSequence>(Crop_Cultivation.this, android.R.layout.simple_spinner_dropdown_item, district_array);
                            districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            district.setAdapter(districtAdapter);
                            ArrayAdapter<CharSequence> cropAdapter = new ArrayAdapter<CharSequence>(Crop_Cultivation.this, android.R.layout.simple_spinner_dropdown_item, crop_array);
                            cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            crop.setAdapter(cropAdapter);
                            //For District spinner
                            district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    district_name = district.getItemAtPosition(i).toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            //For crop spinner
                            crop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                                    crop_name = crop.getItemAtPosition(j).toString();
                                    if (!(district_name == "Select District" || crop_name == "Select Crop")) {
                                        Forecast forecast = new Forecast();
                                        forecast.Forecast(district_name, crop_name, Crop_Cultivation.this);
                                        progress_dialog();
                                    } else {

                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        } catch (Exception e) {
                            Log.d("Connecting server", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("State", state_name);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Crop_Cultivation.this.getApplicationContext());
                requestQueue.add(stringRequest);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.list,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.signout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(getApplicationContext(),login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            return true;
        }if (id==R.id.homeButton){
            Intent intent=new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
        }
        return true;
    }
    public void progress_dialog() {
        final Loading loading=new Loading(Crop_Cultivation.this);
        loading.startloadingDialog();
        Loading.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.dismissDialog();
            }
        },8000);
    }
    }