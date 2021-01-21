package com.example.minecraft.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.minecraft.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getApplicationContext()
                .getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE);
        String token = sharedPref.getString("access_token",null);

        if (token != null) {
        Intent intent = new Intent(MainActivity.this, Profiile.class);
            startActivity(intent);
            finish();
        }
    }


    public void goToRegistrationActivity(View view) {
        Intent intent = new Intent(MainActivity.this, Registration.class);
        startActivity(intent);
        finish();

    }

    public void goToLogActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}