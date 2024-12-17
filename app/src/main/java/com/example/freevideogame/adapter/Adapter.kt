package com.example.freevideogame.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.R
import com.example.freevideogame.databinding.ItemBinding
import com.example.freevideogame.model.GameListResponse
import com.squareup.picasso.Picasso

class Adapter(private val games: List<GameListResponse>, private val onItemSelected:(Int) -> Unit): RecyclerView.Adapter<Adapter.AdapterViewHolder>() {

    class AdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemBinding.bind(view)
        fun bind(game: GameListResponse, onItemSelected:(Int) -> Unit) {
            Picasso.get().load(game.thumbnail).into(binding.ivGame)

            binding.root.setOnClickListener {
                onItemSelected(game.id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return AdapterViewHolder(layoutInflater)
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game, onItemSelected)
    }
}