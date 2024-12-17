package com.example.freevideogame.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.GameDetailsActivity
import com.example.freevideogame.R
import com.example.freevideogame.adapter.CategoryIdAdapter
import com.example.freevideogame.adapter.GameMainAdapter
import com.example.freevideogame.retrofit.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RelevanceFragment: Fragment(R.layout.fragment_relevance) {

    private lateinit var progressBar: ProgressBar
    private lateinit var rvRelevance: RecyclerView

    private var retrofit = RetrofitHelper.getInstace()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progressBar)
        rvRelevance = view.findViewById(R.id.rvRelevance)

        init()
    }

    private fun init() {
        progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGameSortBy("relevance")
            val games = response.body()

            if (games != null) {
                withContext(Dispatchers.Main) {
                    rvRelevance.layoutManager = LinearLayoutManager(context)
                    rvRelevance.adapter = context?.let {
                        CategoryIdAdapter(
                            it,
                            games
                        ) { navigationDetails(it) }
                    }
                    progressBar.isVisible = false
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