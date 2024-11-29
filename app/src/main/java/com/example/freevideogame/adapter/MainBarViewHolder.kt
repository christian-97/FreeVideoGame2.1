package com.example.freevideogame.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.R
import com.example.freevideogame.databinding.ItemMainBarBinding
import com.example.freevideogame.model.MainBarOptions

class MainBarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemMainBarBinding.bind(view)

    fun bind(item: String, isSelected: Boolean) {
        binding.tvOption.text = item

        val color = if (isSelected) R.color.selected else R.color.unselected
        binding.vSeparator.setBackgroundColor(
            ContextCompat.getColor(binding.root.context, color)
        )
    }
}