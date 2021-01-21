package com.example.minecraft.ClassModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TokenModel(
        @SerializedName("access_token") val token: String,
        @SerializedName("refresh_token") val tokenRefresh: String) : Serializable
