package com.example.minecraft.Model

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import com.example.minecraft.ClassModels.TokenModel
import com.example.minecraft.Other.NetworkService
import com.example.minecraft.View.Profiile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class TokenRepository constructor(private val context: Context) {
    var clientId: String = "Здесь должен быть client id"
    var clientSecret: String = "Здесь должен быть client Secret"
    var grantTypePassword: String = "password"
    var grantTypeRefreshToken: String = "refresh_token"
    public var tokenCheck:String = ""

    val liveData = MutableLiveData<String>()


    fun getToken(userNameForToken: String, passwordForToken: String, context: Context): Call<TokenModel> {
        val sharedPreferences = context.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE)

        return NetworkService.getInstance()
                .jsonApi
                .getToken(userNameForToken, passwordForToken, clientId, clientSecret, grantTypePassword)
    }

    fun tokenUpdate () {
        val sharedPref = context.getSharedPreferences("userInfo", MODE_PRIVATE)
        val tokenRefresh = sharedPref!!.getString("token_refresh", null)
        if (tokenRefresh == null) {
            Log.e("token", " refresh token is null")
            return
        }
        Log.d("token", "refresh token - " + tokenRefresh);
        val call: Call<TokenModel> = NetworkService.getInstance()
                .jsonApi
                .getNewToken(tokenRefresh, clientId, clientSecret, grantTypeRefreshToken)
                call.enqueue(object : Callback<TokenModel> {
                    override fun onResponse(call: Call<TokenModel>, response: Response<TokenModel>) {
                        val editor = sharedPref.edit()
                        if (response.isSuccessful) {
                            response.body()?.let {
                                Log.d("token", "refreshed")
                                editor.putString("access_token", it.token)
                                editor.putString("token_refresh", it.tokenRefresh)
                                editor.apply()
                            }
                        }
                    }
                    override fun onFailure(call: Call<TokenModel>, t: Throwable) {
                        t.printStackTrace()
                    }
                })

    }



}