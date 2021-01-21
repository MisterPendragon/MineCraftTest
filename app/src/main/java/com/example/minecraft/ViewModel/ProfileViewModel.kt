package com.example.minecraft.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.minecraft.ClassModels.UserProfile
import com.example.minecraft.Model.ProfileRepository
import com.example.minecraft.Model.TokenRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val profileRepository: ProfileRepository = ProfileRepository(application)

    val liveDataImage = MutableLiveData<String>()
    val liveDataName = MutableLiveData<String>()

    public fun getProfileInformation ()  {
        return profileRepository.getProfileInfo().enqueue(object : Callback<UserProfile> {
            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        liveDataName.value = it.username
                        liveDataImage.value = it.defaultAvatar
                    }
                }
            }

            override fun onFailure(call: Call<UserProfile>, t: Throwable) {}
        })
    }

}