package com.example.minecraft.ClassModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignUpResponseEntity(
        @SerializedName("id") val id: Long,
        @SerializedName("username") val userName: String,
        @SerializedName("email") val email: String) : Serializable