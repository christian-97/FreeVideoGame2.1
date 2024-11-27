package com.example.freevideogame

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.freevideogame.databinding.ActivityGameDetailsBinding
import com.example.freevideogame.databinding.ActivityMainBinding
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
    }
}