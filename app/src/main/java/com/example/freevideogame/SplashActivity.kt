package com.example.freevideogame

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import com.example.freevideogame.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SystemBarsUtil.hideSystemBars(this)

        val shared = getSharedPreferences("LoginData", Context.MODE_PRIVATE)
        val user = shared.getString("name", "").toString()
        val email = shared.getString("email", "").toString()

        Handler(Looper.getMainLooper()).postDelayed({
            if (user.isEmpty()) {
                val intent = Intent(this, BelcomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("name", user)
                    putExtra("email", email)
                }
                startActivity(intent)
                finish()
            }
        }, 5000)

    }
}