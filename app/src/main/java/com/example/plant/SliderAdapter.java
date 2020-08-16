package com.example.plant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ImageView imageViewIcon;
    private TextView textViewTitle;
    private TextView textViewDescription;

    public SliderAdapter(Context context) {
        this.context = context;
    }


    private int[] icons = {
            R.drawable.plantd,R.drawable.weatherd,R.drawable.appicon
    };

    private String[] titles = {
            "Identifying the plant diesease","Crop Cultivation","Welcome"
    };
    private String[] descriptions = {
            "Take a picture of plant and it will provide a solution",
            "Crop growth requires appropriate amount of tempreture.Detailed & accurate historical,real-time & forecast weather information can help the farmers better understand.",
            ""
    };



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        imageViewIcon = view.findViewById(R.id.onboarding_icon_id);
        textViewTitle = view.findViewById(R.id.onboarding_title_id);
        textViewDescription = view.findViewById(R.id.onboarding_description_id);

        imageViewIcon.setImageResource(icons[position]);
        textViewTitle.setText(titles[position]);
        textViewDescription.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
