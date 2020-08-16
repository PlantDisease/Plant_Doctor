package com.example.plant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class BottomNavigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        BottomNavigationView navigationView=findViewById(R.id.nav_view);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id=menuItem.getItemId();
                if(id==R.id.navigation_home)
                {
                    HomeFragment fragment=new HomeFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fm,fragment);
                    fragmentTransaction.commit();
                }

                if(id==R.id.navigation_weather)
                {
                    WeatherFragment fragment= new WeatherFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fm,fragment);
                    fragmentTransaction.commit();
                }

                if(id==R.id.navigation_profile)
                {
                    ProfileFragment fragment=new ProfileFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fm,fragment);
                    fragmentTransaction.commit();
                }


                return true;
            }
        });

        navigationView.setSelectedItemId(R.id.navigation_home);
    }

}
