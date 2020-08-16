package com.example.plant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IntroductionActivity extends AppCompatActivity {
    private ViewPager viewPager;
    IntroPageAdapter introPageAdapter;
    LinearLayout linearLayout;
    private TextView[] dots;
    Button bt1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        List<ScreenItem> mlist = new ArrayList<>();
        mlist.add(new ScreenItem("Identifying the plant diesease","Take a picture of plant and it will provide a solution",R.drawable.plant));
        mlist.add(new ScreenItem("Weather forecasting","Crop growth requires appropriate amount of tempreture.Detailed & accurate historical,real-time & forecast weather information can help the farmers better understand.",R.drawable.weatherfr));

        viewPager=findViewById(R.id.c1);
        introPageAdapter=new IntroPageAdapter(this,mlist);
        viewPager.setAdapter(introPageAdapter);

        linearLayout=(LinearLayout) findViewById(R.id.c2);
        bt1=(Button)findViewById(R.id.bt1);
        adddots(0);

        viewPager.addOnPageChangeListener(viewlistener);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),BottomNavigation.class);
                startActivity(intent);

            }
        });

    }

    public void adddots( int position){
        dots=new TextView[2];
        linearLayout.removeAllViews();
        for(int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.black));

            linearLayout.addView(dots[i]);
        }
        if (dots.length > 0)
        {
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int i) {

            adddots(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
