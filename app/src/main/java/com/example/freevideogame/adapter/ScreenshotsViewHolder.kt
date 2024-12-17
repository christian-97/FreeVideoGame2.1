package com.example.freevideogame.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.databinding.ItemScreenshotsBinding
import com.example.freevideogame.model.Screenshots
import com.squareup.picasso.Picasso

class ScreenshotsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemScreenshotsBinding.bind(view)

    fun bind(screenshots: Screenshots, onItemSelected:(String) -> Unit) {
        Picasso.get().load(screenshots.image).into(binding.ivImage)

        binding.root.setOnClickListener {
            onItemSelected(screenshots.image)
        }
    }
}