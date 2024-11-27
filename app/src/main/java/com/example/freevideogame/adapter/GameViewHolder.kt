package com.example.freevideogame.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.databinding.ItemGameBinding
import com.example.freevideogame.model.GameListResponse
import com.squareup.picasso.Picasso

class GameViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemGameBinding.bind(view)

    fun bind(game: GameListResponse, onItemSelected:(Int) -> Unit) {
        Picasso.get().load(game.thumbnail).into(binding.ivGame)

        binding.root.setOnClickListener {
            onItemSelected(game.id)
        }
    }
}