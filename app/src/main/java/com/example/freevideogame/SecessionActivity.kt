package com.example.freevideogame

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toUpperCase
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.freevideogame.adapter.CategoryIdAdapter
import com.example.freevideogame.adapter.ScreenshotsAdapter
import com.example.freevideogame.databinding.ActivitySecessionBinding
import com.example.freevideogame.model.GameDetailsResponse
import com.example.freevideogame.retrofit.RetrofitHelper
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class SecessionActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecessionBinding

    private var retrofit = RetrofitHelper.getInstace()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getStringExtra("category").toString()
        binding.tvTitle.text = category.toUpperCase()
        init(category)

        binding.tvReturn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }


    private fun init(category: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGameCategory(category)
            val games = response.body()

            if (games != null) {
                withContext(Dispatchers.Main) {
                    binding.rvCategory.layoutManager = LinearLayoutManager(this@SecessionActivity)
                    binding.rvCategory.adapter = CategoryIdAdapter(this@SecessionActivity, games) { navigationDetails(it) }
                    binding.progressBar.isVisible = false
                }
            }
            
        }
    }

    private fun navigationDetails(id: Int) {
        val intent = Intent(this, GameDetailsActivity::class.java)
        intent.putExtra("extra_id", id)
        startActivity(intent)
    }

}