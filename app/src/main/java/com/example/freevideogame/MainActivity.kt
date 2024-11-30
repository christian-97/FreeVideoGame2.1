package com.example.freevideogame

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freevideogame.adapter.GameAdapter
import com.example.freevideogame.adapter.MainBarAdapter
import com.example.freevideogame.databinding.ActivityMainBinding
import com.example.freevideogame.fragment.ViewPagerAdapter
import com.example.freevideogame.model.MainBarOptions
import com.example.freevideogame.retrofit.RetrofitHelper
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

        binding.navView.setNavigationItemSelectedListener(this)

        toogle = ActionBarDrawerToggle(this, binding.main, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.main.addDrawerListener(toogle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        binding.navView.setNavigationItemSelectedListener(this)
        binding.navView.setCheckedItem(R.id.iStart)

        gamesList("", "")

        // ----------------------------------------------------------------
        //binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.rvMainBar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvMainBar.adapter = MainBarAdapter(MainBarOptions.main) { position -> binding.viewPager.currentItem = position }

        // ----------------------------------------------------------------
    }

    private fun gamesList(by: String, selected: String) {
        binding.pb.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = retrofit
            val response = when {
                selected.isEmpty() -> retrofit.getGames()
                by == "platform" -> retrofit.getGamePlatform(selected)
                else -> retrofit.getGameCategory(selected)
            }

            val games = response.body()
            if (games != null) {
                runOnUiThread() {
                    binding.rvGame.layoutManager = GridLayoutManager(this@MainActivity, 2)
                    binding.rvGame.adapter = GameAdapter(games) { navigationDetails(it) }
                    binding.pb.isVisible = false
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

        val platformMap = mapOf(
            R.id.iStart to "",
            R.id.iWindows to "pc",
            R.id.iBrowser to "browser",
            R.id.iAllPlatforms to ""
        )

        val categoryMap = mapOf(
            R.id.iMMORPG to "mmorpg",
            R.id.iShooter to "shooter",
            R.id.iMOBA to "moba",
            R.id.iAnime to "anime",
            R.id.iBattleRoyale to "battle-royale",
            R.id.iStrategyGames to "strategy",
            R.id.iFantasyGames to "fantasy",
            R.id.iSciFi to "sci-fi",
            R.id.iCard to "card",
            R.id.iRacing to "racing",
            R.id.iFighting to "fighting",
            R.id.iSocial to "social",
            R.id.iSports to "sports",

            R.id.iMMO to "mmo",
            R.id.iMMOFPS to "mmofps",
            R.id.iActionRPG to "action-rpg",
            R.id.iSandbox to "sandbox",
            R.id.iOpenWorld to "open-world  ",
            R.id.iSurvival to "survival",
            R.id.iMMOTPS to "mmotps",
            R.id.iPvP to "pvp",
            R.id.iPvE to "pve",
            R.id.iPixel to "pixel",
            R.id.iMMORTS to "mmorts",
            R.id.iAction to "action",
            R.id.iVoxel to "voxel",
            R.id.iZombie to "zombie",
            R.id.iTurnBased to "turn-based",

            R.id.iFirstPerson to "first-person",
            R.id.iThirdPerson to "third-Person",
            R.id.iTopDown to "top-down",
            R.id.i3DGraphics to "3d",
            R.id.i2DGraphics to "2d",
            R.id.iTank to "tank",
            R.id.iSpace to "space",
            R.id.iSailing to "sailing",
            R.id.iSideScroller to "side-scroller",
            R.id.iSuperhero to "superhero",
            R.id.iPermadeath to "permadeath"
        )

        val selectedCategory = categoryMap[item.itemId]
        val selectedPlatform = platformMap[item.itemId]

        //(binding.viewPager.adapter as ViewPagerAdapter).setSelectedData(selectedCategory, selectedPlatform)
        //binding.viewPager.currentItem = 2

        when {
            selectedPlatform != null -> gamesList("platform", selectedPlatform)
            selectedCategory != null -> gamesList("category", selectedCategory)
            item.itemId == R.id.iCloseSession -> {
                confirmLogout()
                val otherItem = binding.navView.menu.findItem(R.id.iStart)
                otherItem.isChecked = false
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