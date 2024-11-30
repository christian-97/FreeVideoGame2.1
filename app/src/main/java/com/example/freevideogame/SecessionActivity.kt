package com.example.freevideogame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.freevideogame.databinding.ActivitySecessionBinding

class SecessionActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecessionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}