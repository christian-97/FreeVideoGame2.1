package com.example.freevideogame

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.freevideogame.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etUser.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val user = binding.etUser.text.toString()
                if (user.isEmpty()) binding.tilUser.error = "Ingrese un usuario valido"
                else binding.tilUser.error = null
            }
            if(hasFocus) {
                val user = binding.etUser.text.toString()
                if (user.isEmpty()) {
                    binding.tilUser.error = null
                    binding.tilUser.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
                    binding.tilUser.boxStrokeColor = ContextCompat.getColor(this, R.color.blue)
                }
            }
        }

        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val password = binding.etPassword.text.toString()
                if (password.isEmpty()) binding.tilPassword.error = "Ingrese una contraseña valido"
                else binding.tilPassword.error = null
            }
            if(hasFocus) {
                val password = binding.etPassword.text.toString()
                if (password.isEmpty()) {
                    binding.tilPassword.error = null
                    binding.tilPassword.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
                    binding.tilPassword.boxStrokeColor = ContextCompat.getColor(this, R.color.blue)
                }
            }
        }

        // =================================================================
        binding.btnStart.setOnClickListener {
            val user = binding.etUser.text.toString()
            val password = binding.etPassword.text.toString()

            if (user.isEmpty()) {
                binding.tilUser.error = getString(R.string.error_user)
                binding.lyMistake.visibility = View.INVISIBLE
            }
            else binding.tilUser.error = null
            if (password.isEmpty()) {
                binding.tilPassword.error = getString(R.string.error_password)
                binding.lyMistake.visibility = View.INVISIBLE
            }
            else binding.tilPassword.error = null

            if (user.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 4 && password.length <= 30) {
                    binding.tilPassword.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
                    if (user == "arial" && password == "123456789") {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else binding.lyMistake.visibility = View.VISIBLE
                }
                else {
                    binding.tilPassword.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)))
                }
            }
        }
    }
}