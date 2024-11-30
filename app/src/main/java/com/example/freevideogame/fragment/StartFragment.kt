package com.example.freevideogame.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.GameDetailsActivity
import com.example.freevideogame.R
import com.example.freevideogame.adapter.GameAdapter
import com.example.freevideogame.retrofit.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartFragment: Fragment(R.layout.fragment_start) {

    private lateinit var rvGame: RecyclerView
    private lateinit var pb: ProgressBar

    private var retrofit = RetrofitHelper.getInstace()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvGame = view.findViewById(R.id.rvGame)
        pb = view.findViewById(R.id.pb)
        gameList()
    }

    fun gameList() {
        if (::pb.isInitialized) pb.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGames()
            val games = response.body()
            if (games != null) {
                withContext(Dispatchers.Main) {
                    rvGame.layoutManager = GridLayoutManager(context, 2)
                    rvGame.adapter = GameAdapter(games) { navigationDetails(it) }
                    pb.isVisible = false
                }
            }
        }
    }

    private fun navigationDetails(id: Int) {
        val intent = Intent(context, GameDetailsActivity::class.java)
        intent.putExtra("extra_id", id)
        startActivity(intent)
    }
}