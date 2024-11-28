package com.example.freevideogame.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.R
import com.example.freevideogame.model.Screenshots

class ScreenshotsAdapter(private val screenshots: List<Screenshots>): RecyclerView.Adapter<ScreenshotsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_screenshots, parent, false)
        return ScreenshotsViewHolder(layoutInflater)
    }

    override fun getItemCount() = screenshots.size

    override fun onBindViewHolder(holder: ScreenshotsViewHolder, position: Int) {
        val item = screenshots[position]
        holder.bind(item)
    }
}