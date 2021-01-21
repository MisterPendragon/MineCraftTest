package com.example.minecraft.Other

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.minecraft.Model.TokenRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AccessTokenInterceptor constructor(
        private val repository: TokenRepository,
        private val preferences: SharedPreferences) : Interceptor {

    private var refreshCounter = 0


    override fun intercept(chain: Interceptor.Chain): Response {

        val token: String? = preferences.getString("access_token", null)

        return if (token != null) {
            Log.d("token", "not null")
            val request:Request = requestWithAuth(chain.request(), token)

            val response = chain.proceed(request)
            if (response.code == 401) {
                Log.e("token", "401")
                if (canRefresh()) {
                    repository.tokenUpdate()
                    incRefresh()
                } else {
                    refreshCounter = 0
                }

            }
            return response
        }
        else {
            Log.e("token", "null")
            chain.proceed(chain.request())
        }

    }

    private fun requestWithAuth (request: Request, accessToken: String): Request{
        val token = if (refreshCounter > 1) accessToken else  accessToken
        return request.newBuilder()
                .addHeader("Authorization", "Bearer $token") // предоставить доступ к предъявителю этого токена
                .build()

    }

    private fun incRefresh() {
        refreshCounter++
    }

    private fun canRefresh() = refreshCounter < 3

}



