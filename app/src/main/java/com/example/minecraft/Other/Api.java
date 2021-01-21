package com.example.minecraft.Other;

import com.example.minecraft.ClassModels.SignUpResponseEntity;
import com.example.minecraft.ClassModels.TokenModel;
import com.example.minecraft.ClassModels.UserProfile;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface Api {

    @FormUrlEncoded
    @POST("/api/users/register")
    Call<SignUpResponseEntity> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("/api/o/token/")
    Call<TokenModel> getToken(
            @Field("username") String username,
            @Field("password") String password,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType
    );
    @FormUrlEncoded
    @POST("/api/o/token/")
    Call<TokenModel> getNewToken(
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType
    );

    @GET("/api/users/profile")
    Call<UserProfile> getProfile();



}
