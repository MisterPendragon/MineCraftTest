package com.example.minecraft.Model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.minecraft.ClassModels.UserProfile
import com.example.minecraft.Other.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository(application: Application)  {
    var application: Application? = application

    fun getProfileInfo(): Call<UserProfile> = NetworkService.getInstance()
            .jsonApi
            .profile


}