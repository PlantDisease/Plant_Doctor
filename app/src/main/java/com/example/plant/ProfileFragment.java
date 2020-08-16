package com.example.plant;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    Button pf4;
    private static final String URL = "https://savewithblood.000webhostapp.com/userlogin.php";
    TextView pf2,pf3,pf5,pf6;
    ImageView pf1;
    static String Name,Des,name,des;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final String number="9880920825";
        pf4 = view.findViewById(R.id.pf4);
        pf2=view.findViewById(R.id.pf3);
        pf3=view.findViewById(R.id.pf2);
        pf5=view.findViewById(R.id.pf5);
        pf6=view.findViewById(R.id.pf6);
        pf1=view.findViewById(R.id.pf1);

        Name=pf2.getText().toString();
        Des=pf3.getText().toString();
        pf4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonresponse;
                        try {
                            jsonresponse = new JSONObject(response);
                            String result=jsonresponse.getString("success");
                            Log.d("Msgfromuser",result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Connecting to server",e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Number",number);
                        params.put("Name",Name);
                        params.put("Desc",Des);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });


        pf6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(false);
                dialog.setTitle("Account");
                dialog.setMessage("Are you sure you want to SignOut?" );
                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(getActivity(),login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                    }
                })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Action for "Cancel".
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });
        return view;

    }

}














