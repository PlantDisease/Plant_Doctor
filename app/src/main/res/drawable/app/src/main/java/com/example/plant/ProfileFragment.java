package com.example.plant;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    Button pf4;
    TextView pf2,pf3,pf5,pf6;
    ImageView pf1;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        pf4 = view.findViewById(R.id.pf4);
        pf2=view.findViewById(R.id.pf3);
        pf3=view.findViewById(R.id.pf2);
        pf5=view.findViewById(R.id.pf5);
        pf6=view.findViewById(R.id.pf6);
        pf1=view.findViewById(R.id.pf1);

        return view;

    }

}
