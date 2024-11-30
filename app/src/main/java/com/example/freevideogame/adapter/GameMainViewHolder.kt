package com.example.freevideogame.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.databinding.ItemGameBinding
import com.example.freevideogame.databinding.ItemGameMainBinding
import com.example.freevideogame.model.GameListResponse
import com.squareup.picasso.Picasso

class GameMainViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemGameMainBinding.bind(view)

    fun bind(game: GameListResponse, onItemSelected:(Int) -> Unit) {
        Picasso.get().load(game.thumbnail).into(binding.ivGame)
        binding.tvTitle.text = game.title
        binding.tvCategory.text = game.genre
        binding.tvDate.text = "Release Date:  ${game.release_date}"
        binding.tvPlatform.text = game.platform

        binding.root.setOnClickListener {
            onItemSelected(game.id)
        }
    }
}