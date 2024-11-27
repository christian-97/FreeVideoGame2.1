package com.example.freevideogame

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.freevideogame.adapter.GameAdapter
import com.example.freevideogame.databinding.ActivityMainBinding
import com.example.freevideogame.retrofit.RetrofitHelper
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toogle: ActionBarDrawerToggle
    private  val retrofit = RetrofitHelper.getInstace()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.tbMain)
        setSupportActionBar(toolbar)

        resources.getColor(R.color.blue, theme)

        toogle = ActionBarDrawerToggle(this, binding.main, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.main.addDrawerListener(toogle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        binding.navView.setNavigationItemSelectedListener(this)
        binding.navView.setCheckedItem(R.id.iStart)

        listGame()
    }

    private fun listGame() {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = retrofit.getGames()
            val response = retrofit.body()
            if (response != null) {
                runOnUiThread {
                    //binding.rvGame.setHasFixedSize(true)
                    binding.rvGame.layoutManager = GridLayoutManager(this@MainActivity, 2)
                    binding.rvGame.adapter = GameAdapter(response) {navigationDetails(it)}
                }
            }
        }
    }

    private fun navigationDetails(id: Int) {
        val intent = Intent(this, GameDetailsActivity::class.java)
        intent.putExtra("extra_id", id)
        startActivity(intent)
    }

    // =================================================================================================
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.iCloseSession -> {
                confirmLogout()
                val otherItem = binding.navView.menu.findItem(R.id.iStart)
                otherItem.isChecked = true
            }
        }
        binding.main.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toogle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toogle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }
    // =================================================================================================


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