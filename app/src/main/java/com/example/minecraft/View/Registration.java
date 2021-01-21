package com.example.minecraft.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minecraft.ClassModels.SignUpResponseEntity;
import com.example.minecraft.Other.NetworkService;
import com.example.minecraft.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;

public class Registration extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button button;
    EditText mail;
    EditText userName;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        button = findViewById(R.id.buttonOfReg);
        mail = findViewById(R.id.mailOfRegistration);
        userName = findViewById(R.id.nameOfUserRegistrationId);
        password = findViewById(R.id.passwordOfRegistration);
        sharedPreferences = getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Call<SignUpResponseEntity> call = NetworkService
                        .getInstance()
                        .getJsonApi()
                        .createUser(mail.getText().toString(),password.getText().toString(),userName.getText().toString());
                call.enqueue(new Callback<SignUpResponseEntity>() {
                    @Override
                    public void onResponse(Call<SignUpResponseEntity> call, Response<SignUpResponseEntity> response) {
                        try {
                            if (response.isSuccessful()) {
                                Toast.makeText(Registration.this,"Registration successful", LENGTH_LONG).show();
                                Intent intent = new Intent(Registration.this, Profiile.class);
                                startActivity(intent);
                                finish();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userName",userName.getText().toString());
                                editor.putString("password",password.getText().toString());
                                editor.apply();
                            } else {
                               Log.i("TAG"," -> " + response.code());
                            } }
                        catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpResponseEntity> call, Throwable t) {
                        Toast.makeText(Registration.this, t.getMessage(), LENGTH_LONG).show();
                    }
                });
            }
        });
    }

}