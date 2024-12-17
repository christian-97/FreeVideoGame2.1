package com.example.freevideogame

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.freevideogame.databinding.ActivityMainBinding
import com.example.freevideogame.databinding.FragmentVoidBinding
import com.example.freevideogame.fragment.AboutFragment
import com.example.freevideogame.fragment.CategoryFragment
import com.example.freevideogame.fragment.FavoriteFragment
import com.example.freevideogame.fragment.FavoriteVoidFragment
import com.example.freevideogame.fragment.MainFragment
import com.example.freevideogame.fragment.StartFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var toogle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // =================================================================
        val shared = getSharedPreferences("LoginData", Context.MODE_PRIVATE)
        val name = shared.getString("name", "").toString()
        val email = shared.getString("email", "").toString()

        // ----------------------------------------------------------------
        var textName : String? = null
        var textEmail : String? = null
        db.collection("User")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.data["email"].toString() == email) {
                        val textFirst = document.get("firstName").toString()
                        val textLast = document.get("lastName").toString()

                        textName = "$textFirst $textLast"
                        textEmail = document.get("email").toString()

                    }
                }
            }
        // ----------------------------------------------------------------


        val username: ImageView = findViewById(R.id.ivUser)

        username.setOnClickListener { view ->

            val inflater = LayoutInflater.from(this)
            val popupView = inflater.inflate(R.layout.custom_menu_layout, null)
            
            // ----------------------------------------------------------------
            val textView: TextView = popupView.findViewById(R.id.name)
            val text = "Neat Idea Co"
            val spannableString = SpannableString(text)

            val color = ContextCompat.getColor(this, R.color.blue)
            spannableString.setSpan(ForegroundColorSpan(color), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(ForegroundColorSpan(color), 5, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(ForegroundColorSpan(color), 11, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            textView.text = spannableString
            // ----------------------------------------------------------------

            val tvName: TextView = popupView.findViewById(R.id.textViewName)
            val tvEmail: TextView = popupView.findViewById(R.id.textViewEmail)

            // =================================================================
            if (textName != null) {
                tvName.text =  textName
                tvEmail.text = textEmail
            }
            else {
                tvName.text =  name
                tvEmail.text = email
            }
            // =================================================================


            val widthInPx = (280 * resources.displayMetrics.density).toInt()
            val heightInPx = (460 * resources.displayMetrics.density).toInt()

            val popupWindow = PopupWindow(popupView, widthInPx, heightInPx, true)
            popupWindow.showAsDropDown(view, -100, 0)

            val logOut = popupView.findViewById<LinearLayout>(R.id.logOut)
            logOut.setOnClickListener {
                confirmLogout()
                popupWindow.dismiss()
            }

            val account = popupView.findViewById<LinearLayout>(R.id.account)
            account.setOnClickListener {
                val intent = Intent(this, AccountActivity::class.java).apply {
                    putExtra("email", email)
                }
                startActivity(intent)
            }
        }

        // =================================================================


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

        // ----------------------------------------------------------------

        // ----------------------------------------------------------------
        if (savedInstanceState == null) {
            binding.chipNavigationBar.setItemSelected(R.id.home, true)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment())
                .commit()
        }

        binding.chipNavigationBar.setOnItemSelectedListener { id ->
            when (id) {
                R.id.home -> replaceFragment(MainFragment())
                R.id.favorite -> layout()
                R.id.library -> replaceFragment(StartFragment())
                R.id.about -> replaceFragment(AboutFragment())
                else -> {}
            }
        }
        // ----------------------------------------------------------------
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun layout() {
        val shared = getSharedPreferences("LoginData", Context.MODE_PRIVATE)
        val email = shared.getString("email", "").toString()

        db.collection("Favorite")
            .get()
            .addOnSuccessListener { result ->
                val idList = result.mapNotNull { document ->
                    val mail = document.get("email").toString()
                    val id = document.get("id")?.toString()?.toIntOrNull()
                    if (email == mail) id else null
                }

                val fragment = if (idList.isNotEmpty()) {
                    FavoriteFragment()
                } else {
                    FavoriteVoidFragment()
                }

                replaceFragment(fragment)
            }
    }

    // =================================================================================================
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val platformMap = mapOf(
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
            R.id.iOpenWorld to "open-world",
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

        when {
            selectedPlatform != null -> tag("platform", selectedPlatform)
            selectedCategory != null -> tag("category", selectedCategory)

            /*item.itemId == R.id.iCloseSession -> {
                confirmLogout()
                val otherItem = binding.navView.menu.findItem(R.id.iStart)
                otherItem.isChecked = false
                binding.main.closeDrawer(GravityCompat.START)
            }*/

            /*item.itemId == R.id.iStart -> {
                binding.viewPager.setCurrentItem(0, true)
                binding.main.closeDrawer(GravityCompat.START)
            }*/
        }

        return true
    }

    private fun tag(by: String, selected: String) {
        val intent = Intent(this, TagActivity::class.java).apply {
            putExtra("by", by)
            putExtra("selected", selected)
        }
        startActivity(intent)
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
        val logoutDialog = LogoutDialogFragment() {closeSession()}
        logoutDialog.show(supportFragmentManager, "LogoutDialog")
    }

    private fun closeSession() {
        deleteSession()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun deleteSession() {
        val shared = getSharedPreferences("LoginData", Context.MODE_PRIVATE)
        val edit = shared.edit()
        edit.clear()
        edit.remove("user")
        edit.apply()
    }
}

class LogoutDialogFragment(private val onClick:() -> Unit) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_logout, container, false)

        val btnAccept = view.findViewById<TextView>(R.id.btn_accept)
        val btnCancel = view.findViewById<TextView>(R.id.btn_cancel)

        btnAccept.setOnClickListener {
            onClick()
            dismiss()
        }

        btnCancel.setOnClickListener { dismiss() }

        return view
    }
}