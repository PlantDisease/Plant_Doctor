package com.example.plant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.androdocs.httprequest.HttpRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Home extends AppCompatActivity {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.leaf1, R.drawable.leaf2, R.drawable.leaf3, R.drawable.leaf4};

    CardView mainContainer, Health_check, Crop_cultivation, Profile_, About_;
    ProgressBar loader;
    String CITY = "Mangalore,in";
    String API = "8118ed6ee68db2debfaaa5a44c832918";
    TextView addressTxt, updated_atTxt, tempTxt, errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        Health_check = findViewById(R.id.Health_check);
        Crop_cultivation = findViewById(R.id.Crop_cultivation);
        Profile_ = findViewById(R.id.Profile_);
        About_ = findViewById(R.id.About_);

        addressTxt = findViewById(R.id.address);
        updated_atTxt = findViewById(R.id.updated_at);
        tempTxt = findViewById(R.id.temp);
        loader = findViewById(R.id.loader);
        mainContainer = findViewById(R.id.mainContainer);
        errorText = findViewById(R.id.errorText);
        new weatherTask().execute();

        Health_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadFile.class);
                startActivity(intent);
            }
        });

        Crop_cultivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Crop_Cultivation.class);
                startActivity(intent);
            }
        });

        Profile_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register_.class);
                startActivity(intent);
            }
        });

        About_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), About.class);
                startActivity(intent);
            }
        });


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
                String updatedAtText = "" + new SimpleDateFormat("dd/MM/yyyy \n hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
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
                    SharedPreferences preferences=getSharedPreferences("Login",0);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.remove("First");
                    editor.commit();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
            return true;
        }if (id==R.id.homeButton){
            Intent intent=new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
        }
        return true;
    }
}