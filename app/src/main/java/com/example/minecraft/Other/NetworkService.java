package com.example.minecraft.Other;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minecraft.App;
import com.example.minecraft.Model.TokenRepository;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://minesrv.ey.r.appspot.com";
    private Retrofit mRetrofit;

    private NetworkService () {
        TokenRepository tokenRepository = new TokenRepository(App.instance);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        SharedPreferences preferences = App.Companion.getInstance()
                .getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new AccessTokenInterceptor(tokenRepository, preferences))
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance(){
        if(mInstance == null){ mInstance = new NetworkService(); }
        return mInstance;
    }

    public Api getJsonApi() {return mRetrofit.create(Api.class);}
}
