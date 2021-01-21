package com.example.minecraft.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.minecraft.R
import com.example.minecraft.ViewModel.TokenViewModel
import kotlinx.android.synthetic.main.activity_profiile.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private var userName: String = " "
    private var password: String = " "

    private lateinit var tokenViewModel: TokenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        tokenViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(TokenViewModel::class.java)
        buttonSignIn.setOnClickListener { signIn() }
        observeAuth()
    }

    private fun signIn(){
        userName = userNameSignIn.text.toString().trim()
        password = passwordSignInId.text.toString().trim()

        if (userName.isEmpty()) return
        if (password.isEmpty() || password.length < 6) return
        tokenViewModel.getToken(userName, password)
    }

    private fun observeAuth() {

        tokenViewModel.liveDataAuth.observe(this, Observer {
            Log.i("LogJo", "Получил токен для проверки в SignInActivity = $it")
            if (it.isNotEmpty()) {
                startActivity(Intent(this, Profiile::class.java))
                finish()
            }
        })
    }


}