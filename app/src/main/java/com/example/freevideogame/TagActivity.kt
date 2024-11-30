package com.example.freevideogame

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.freevideogame.adapter.GameAdapter
import com.example.freevideogame.databinding.ActivityTagBinding
import com.example.freevideogame.retrofit.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TagActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTagBinding
    private val retrofit = RetrofitHelper.getInstace()

    companion object {
        const val by: String = "by"
        const val selected = "selected"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val by = intent.getStringExtra(by).toString()
        val selected = intent.getStringExtra(selected).toString()
        gameListBy(by, selected)

        binding.ivReturn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.tvTitle.text = selected.replaceFirstChar { it.uppercaseChar() }
    }

    private fun gameListBy(by: String, selected: String) {
        binding.pb.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = when {
                selected.isEmpty() -> retrofit.getGames()
                by == "platform" -> retrofit.getGamePlatform(selected)
                else -> retrofit.getGameCategory(selected)
            }
            val games = response.body()
            if (games != null) {
                withContext(Dispatchers.Main) {
                    binding.rvGame.layoutManager = GridLayoutManager(this@TagActivity, 2)
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
}