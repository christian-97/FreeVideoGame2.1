package com.example.freevideogame

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import com.example.freevideogame.databinding.ActivityLoadingBinding

class LoadingActivity : AppCompatActivity() {
    lateinit var  binding: ActivityLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SystemBarsUtil.hideSystemBars(this)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}