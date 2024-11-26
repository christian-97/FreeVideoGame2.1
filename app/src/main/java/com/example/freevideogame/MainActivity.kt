package com.example.freevideogame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.freevideogame.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivExit.setOnClickListener {
            confirmLogout()
        }
    }

    private fun confirmLogout() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Cerrar sessión")
        alert.setMessage("¿Estás seguro de cerrar la sesión?")
        alert.setCancelable(false)
        alert.setPositiveButton("Aceptar") { dialog, which ->
            closeSession()
        }
        alert.setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }
        alert.show()
    }

    private fun closeSession() {
        deleteSession()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun deleteSession() {
        var shared = getSharedPreferences("LoginData", Context.MODE_PRIVATE)
        val edit = shared.edit()
        edit.clear()
        edit.remove("user")
        edit.commit()
    }
}