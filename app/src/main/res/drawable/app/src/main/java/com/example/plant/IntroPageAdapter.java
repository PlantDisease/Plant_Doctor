package com.example.plant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroPageAdapter extends PagerAdapter {
    Context context;
    List<ScreenItem> list;

    public IntroPageAdapter(Context context, List<ScreenItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen,null);

        ImageView imgslide= layoutScreen.findViewById(R.id.phone);
        TextView title=layoutScreen.findViewById(R.id.l1);
        TextView desc=layoutScreen.findViewById(R.id.l2);


        title.setText(list.get(position).getTitle());
        desc.setText(list.get(position).getDesp());
        imgslide.setImageResource(list.get(position).getScrnImg());

        container.addView(layoutScreen);

        return layoutScreen;


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
