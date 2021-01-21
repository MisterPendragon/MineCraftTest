package com.example.minecraft.ClassModels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserProfile( @SerializedName("username") var username: String,
                   @SerializedName("avatar") var defaultAvatar: String):Serializable {
}