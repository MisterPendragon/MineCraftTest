package com.example.minecraft.ViewModel

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.minecraft.ClassModels.TokenModel
import com.example.minecraft.Model.TokenRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenViewModel(application: Application) : AndroidViewModel(application) {
    private val tokenRepository:TokenRepository = TokenRepository(application)
    private val sharedPref = application.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)
    val liveDataAuth = MutableLiveData<String>()

    fun getToken (userNameEditText:String,passwordEditText:String) {
        tokenRepository.getToken(userNameEditText,passwordEditText,getApplication())
                .enqueue(object : Callback<TokenModel> {
                    override fun onResponse(call: Call<TokenModel>, response: Response<TokenModel>) {
                        val editor = sharedPref.edit()
                        if (response.isSuccessful) {
                            response.body()?.let {
                                editor.putString("access_token", it.token)
                                editor.putString("token_refresh", it.tokenRefresh)
                                editor.apply()
                                liveDataAuth.value = it.token
                            }
                        }
                    }

                    override fun onFailure(call: Call<TokenModel>, t: Throwable) {}
                })
    }

}