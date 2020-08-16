package com.example.plant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import am.appwise.components.ni.NoInternetDialog;

public class login extends AppCompatActivity {
    private Spinner l1;
    private EditText l3;
    private ImageView sign;
    private Button l4;
    private TextView l2,l5;
    static String number;

    public static String getnumber() {
        return number;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    l5=findViewById(R.id.l5);
    sign=findViewById(R.id.sign);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,sign_in.class);
                startActivity(intent);
            }
        });
    String text="We will send an One Time Password on this mobile number ";
    SpannableString ss = new SpannableString(text);
    StyleSpan boldspan =new StyleSpan(Typeface.BOLD);
    ss.setSpan(boldspan,16,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    l5.setText(ss);
        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(this)
                .build();
    l1 = findViewById(R.id.l1);
        l1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        l3 = findViewById(R.id.phone);

        findViewById(R.id.l4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[l1.getSelectedItemPosition()];

                number = l3.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    l3.setError("Valid number is required");
                    l3.requestFocus();
                    return;
                }

                String phoneNumber = "+" + code + number;

                Intent intent = new Intent(login.this, otp.class);
                intent.putExtra("phonenumber", phoneNumber);
                startActivity(intent);

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, Intro.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }

}
