package com.example.minecraft.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.minecraft.R
import com.example.minecraft.ViewModel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profiile.*

class Profiile : AppCompatActivity() {
    var profileViewModel:ProfileViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiile)
        val buttonLogOut:Button = findViewById(R.id.LogOutId)

        profileViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(ProfileViewModel::class.java)
        subscribe()
        buttonLogOut.setOnClickListener { logOut() }
        getProfileInfo ()

    }

    private fun subscribe() {
        profileViewModel?.liveDataImage?.observe(this, {
            Glide.with(this)
                    .load(it.toString())
                    .into(imageViewProfile)
        })
        profileViewModel?.liveDataName?.observe(this, {
            textViewProfile.text = it.toString()
        })


    }

    fun getProfileInfo () {

         profileViewModel?.getProfileInformation()

    }

    fun logOut() {
        val prefs = applicationContext.getSharedPreferences("userInfo", MODE_PRIVATE)
        prefs.edit().remove("access_token").apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}