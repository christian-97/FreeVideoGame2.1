package com.example.freevideogame

import android.os.Bundle
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
    lateinit var binding: ActivityGameDetailsBinding
    private  val retrofit = RetrofitHelper.getInstace()

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: Int = intent.getIntExtra(EXTRA_ID, 0)
        getGameInformation(id)
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
}