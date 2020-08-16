package com.example.plant;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    public WeatherFragment() {
        // Required empty public constructor
    }

    private static final String[] paths = {"Andaman Nicobar Islands", "item 2", "item 3"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview=inflater.inflate(R.layout.fragment_weather, container, false);
        Spinner state = (Spinner)rootview.findViewById(R.id.state) ;
        Spinner district=(Spinner) rootview.findViewById(R.id.district);
        Spinner crop=(Spinner)rootview.findViewById(R.id.crop);
        TextView production=(TextView) rootview.findViewById(R.id.result);
        EditText acre=(EditText) rootview.findViewById(R.id.acre);
        Button calculateButton=(Button) rootview.findViewById(R.id.calculate);
        TextView calculated=(TextView) rootview.findViewById(R.id.Calculated);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

}
