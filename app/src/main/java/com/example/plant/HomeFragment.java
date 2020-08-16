package com.example.plant;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.androdocs.httprequest.HttpRequest;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CarouselView carouselView;
    ConstraintLayout mainContainer;
    ProgressBar loader;
    int PERMISSION_ID = 44;
    String CITY = "udupi,in";
    String API = "8118ed6ee68db2debfaaa5a44c832918";
    String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
    TextView addressTxt, updated_atTxt, tempTxt,errorText;
    Button hfgal;
    int[] sampleImages = {R.drawable.leaf1, R.drawable.leaf2, R.drawable.leaf3, R.drawable.leaf4};


    public HomeFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootview=inflater.inflate(R.layout.fragment_home, container, false);
        hfgal=rootview.findViewById(R.id.hfcam);
        carouselView = rootview.findViewById(R.id.carouselView);
        addressTxt = rootview.findViewById(R.id.address);
        updated_atTxt =rootview.findViewById(R.id.updated_at);
        tempTxt = rootview.findViewById(R.id.temp);
        loader=rootview.findViewById(R.id.loader);
        mainContainer=rootview.findViewById(R.id.mainContainer);
        errorText=rootview.findViewById(R.id.errorText);
        carouselView.setPageCount(sampleImages.length);
        new weatherTask().execute();
        carouselView.setImageListener(imageListener);

        hfgal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),UploadFile.class);
                startActivity(intent);
            }
        });


        return rootview;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* Showing the ProgressBar, Making the main design GONE */
            loader.setVisibility(View.VISIBLE);
            mainContainer.setVisibility(View.GONE);
            errorText.setVisibility(View.GONE);
        }
        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            return response;
        }
        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";

                String address = jsonObj.getString("name") + ", " + sys.getString("country");


                /* Populating extracted data into our views */
                addressTxt.setText(address);
                updated_atTxt.setText(updatedAtText);
                tempTxt.setText(temp);

                /* Views populated, Hiding the loader, Showing the main design */
                loader.setVisibility(View.GONE);
                mainContainer.setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                loader.setVisibility(View.GONE);
                errorText.setVisibility(View.VISIBLE);
            }

        }
    }

}





