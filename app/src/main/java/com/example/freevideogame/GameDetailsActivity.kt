package com.example.freevideogame

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.freevideogame.adapter.ScreenshotsAdapter
import com.example.freevideogame.databinding.ActivityGameDetailsBinding
import com.example.freevideogame.model.GameDetailsResponse
import com.example.freevideogame.retrofit.RetrofitHelper
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailsBinding
    private  val retrofit = RetrofitHelper.getInstace()

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: Int = intent.getIntExtra(EXTRA_ID, 0)

        //Log.d("Hola", id.toString())
        getGameInformation(id)

        binding.ivReturn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun getGameInformation(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGameDetails(id)

            if(response.body() != null) {
                runOnUiThread {
                    createUI(response.body()!!)
                }
            }
        }
    }

    private fun createUI(game: GameDetailsResponse) {
        Picasso.get().load(game.thumbnail).into(binding.ivGame)

        binding.tvTitle.text = game.title
        binding.tvRealaseDate.text = "Realase Date:   ${game.release_date}"
        binding.tvShortDescription.text = game.short_description

        binding.tvAbout.text = "Game Description (${game.title})"
        binding.tvDescription.text = game.description

        // =================================================================
        if (game.minimum_system_requirements?.os == null) {
            binding.tvRequirements.isVisible = false

            binding.tvOS.isVisible = false; binding.tvTitleOS.isVisible = false
            binding.tvProcessor.isVisible = false; binding.tvTitleProcessor.isVisible = false
            binding.tvMemory.isVisible = false; binding.tvTitleMemory.isVisible = false
            binding.tvGraphics.isVisible = false; binding.tvTitleGraphics.isVisible = false
            binding.tvStorage.isVisible = false; binding.tvTitleStorage.isVisible = false
        }
        else {
            binding.tvOS.text = game.minimum_system_requirements.os
            binding.tvProcessor.text = game.minimum_system_requirements.processor
            binding.tvMemory.text = game.minimum_system_requirements.memory
            binding.tvGraphics.text = game.minimum_system_requirements.graphics
            binding.tvStorage.text = game.minimum_system_requirements.storage
        }
        // =================================================================

        binding.tvATitle.text = game.title
        binding.tvDeveloper.text = game.developer
        binding.tvPublisher.text = game.publisher
        binding.tvGenre.text = game.genre
        binding.tvPlatform.text = game.platform

        binding.linkProfile.setOnClickListener { webView(game.freetogame_profile_url)}
        binding.linkGame.setOnClickListener { webView(game.game_url) }
        binding.linkPrivacy.setOnClickListener { webView("https://www.freetogame.com/privacy-policy")}

        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val totalItems = game.screenshots.size
                return if (totalItems == 3 && position == 2) 2
                       else 1
            }
        }
        binding.rvImage.layoutManager = gridLayoutManager
        binding.rvImage.adapter = ScreenshotsAdapter(game.screenshots)

    }

    fun webView(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}