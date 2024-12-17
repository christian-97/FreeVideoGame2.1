package com.example.freevideogame.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.GameDetailsActivity
import com.example.freevideogame.R
import com.example.freevideogame.adapter.PlatformAdapter
import com.example.freevideogame.retrofit.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlatformFragment:  Fragment(R.layout.fragment_platform) {

    private lateinit var btnPc : ImageView
    private lateinit var btnWeb: ImageView

    private lateinit var rvPlatform: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var retrofit = RetrofitHelper.getInstace()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPc = view.findViewById(R.id.btnPc)
        btnWeb = view.findViewById(R.id.btnWeb)
        rvPlatform = view.findViewById(R.id.rvPlatform)
        progressBar = view.findViewById(R.id.progressBar)
        // ----------------------------------------------------------------

        btnPc.setOnClickListener {
            animate(btnPc)
            init("pc")
        }

        btnWeb.setOnClickListener {
            animate(btnWeb)
            init("browser")
        }

        init("pc")
    }

    private fun init(platform: String) {
        if (::progressBar.isInitialized) progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGamePlatform(platform)
            val games = response.body()
            if (games != null) {
                withContext(Dispatchers.Main) {
                    rvPlatform.layoutManager = LinearLayoutManager(context)
                    rvPlatform.adapter = context?.let { PlatformAdapter(it, games) {navigationDetails (it)} }
                }
                progressBar.isVisible = false
            }
        }
    }

    private fun navigationDetails(id: Int) {
        val intent = Intent(context, GameDetailsActivity::class.java)
        intent.putExtra("extra_id", id)
        startActivity(intent)
    }

    private fun animate(image: ImageView) {
        val color = context?.let { ContextCompat.getColor(it,R.color.email) }
        if (color != null) {
            image.setColorFilter(color)
        }

        image.animate()
            .scaleX(1.2f)
            .scaleY(1.2f)
            .setDuration(300)
            .withEndAction {

                image.clearColorFilter()
                image.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(300)
                    .start()
            }
            .start()
    }
}
