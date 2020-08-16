package com.example.plant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RecyclerView recycler= this.<RecyclerView>findViewById(R.id.recycler);
        b1=findViewById(R.id.b1);
        recycler.setLayoutManager(new LinearLayoutManager(this));
//        String[] data={"Apple","Pineapple","Banana","Mango","Paddy","Corn","Chilli","Beans"};
        List<model> data = new ArrayList<>();
        data.add(new model("Apple", R.drawable.apple));
        data.add(new model("Blueberry", R.drawable.blueberry));
        data.add(new model("Cherry",R.drawable.cherry));
        data.add(new model("Corn",R.drawable.corn));
        data.add(new model("Grape",R.drawable.grape));
        data.add(new model("Orange",R.drawable.orange));
        data.add(new model("Peach",R.drawable.peach));
        data.add(new model("Pepper",R.drawable.chilli));

        recycler.setAdapter(new Myadapter(data, new Myadapter.OnClickListener() {
            @Override
            public void onClick(model item) {
                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }
        ));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),BottomNavigation.class);
                startActivity(intent);

            }
        });

    }

}
