package com.example.plant;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.media.MediaRecorder.VideoSource.CAMERA;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CarouselView carouselView;
    Button hfcam,hfgal;
    ImageView imageView;
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
        imageView=rootview.findViewById(R.id.imageView);
        carouselView = rootview.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

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


}



