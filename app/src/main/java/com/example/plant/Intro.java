package com.example.plant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

import am.appwise.components.ni.NoInternetDialog;

public class Intro extends AppCompatActivity implements Button.OnClickListener {

    private ViewPager viewPager;
    private LinearLayout dotsLinearLayout;
    private SliderAdapter adapter;
    private TextView[] dots;
    private int noOfScreens;
    private Button backButton;
    private Button nextButton;
    private int currentScreenIndex;
    SharedPreferences sharedPreferences;
    Boolean Firsttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setPreference();
//        sharedPreferences = getSharedPreferences("pref",MODE_PRIVATE);
//        sharedPreferences.getBoolean("firsttime",true);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(this)
                .build();
        initializeViews();
        currentScreenIndex = 0;
        adapter = new SliderAdapter(this);
        viewPager.setAdapter(adapter);

        noOfScreens = adapter.getCount();
        addDotsToArray(noOfScreens, currentScreenIndex);

        viewPager.addOnPageChangeListener(listener);




    }

    private void initializeViews() {
        viewPager = findViewById(R.id.onboarding_viewpager_id);
        dotsLinearLayout = findViewById(R.id.onboarding_dots_container_id);
        nextButton = findViewById(R.id.next_button_id);
        backButton = findViewById(R.id.back_button_id);

        nextButton.setText(getString(R.string.next));
        backButton.setEnabled(false);

        nextButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }


    private void addDotsToArray(int noOfSlides, int currentPositon) {
        dots = new TextView[noOfSlides];


        for (int i = 0; i < noOfSlides; i++) {
            dots[i] = new TextView(this);

            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(40);
            dots[i].setTextColor(ContextCompat.getColor(this, R.color.black));
        }

        dots[currentPositon].setTextColor(ContextCompat.getColor(this, R.color.white));

        displayDots(dots);
    }

    private void displayDots(TextView[] dots) {
        // Add dots to the layout
        for (int i = 0; i < dots.length; i++) {
            dotsLinearLayout.addView(dots[i]);
        }
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            currentScreenIndex = i;

            dotsLinearLayout.removeAllViews();

            addDotsToArray(noOfScreens, i);

            if (i == 0) {
                nextButton.setText(getString(R.string.next));
                backButton.setText("");
                backButton.setEnabled(false);
                nextButton.setEnabled(true);
            } else if (i == noOfScreens - 1) {
                nextButton.setText(getString(R.string.finish));
                backButton.setText(getString(R.string.back));
                nextButton.setEnabled(true);
                backButton.setEnabled(true);
            } else {
                nextButton.setText(getString(R.string.next));
                nextButton.setEnabled(true);
                backButton.setText(getString(R.string.back));
                backButton.setEnabled(true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button_id:
                String buttonText = nextButton.getText().toString();
                if (buttonText.equals(this.getResources().getString(R.string.next))) {
                    viewPager.setCurrentItem(currentScreenIndex + 1, true);
                } else if (buttonText.equals(this.getResources().getString(R.string.finish))) {

                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                }
                break;
            case R.id.back_button_id:
                viewPager.setCurrentItem(currentScreenIndex - 1, true);
                break;
        }
    }
    private void setPreference()
    {
        SharedPreferences preferences=getSharedPreferences("Login",0);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("First","Yes");
        Log.d("Inside setPreference","done");
        editor.commit();
    }
}
