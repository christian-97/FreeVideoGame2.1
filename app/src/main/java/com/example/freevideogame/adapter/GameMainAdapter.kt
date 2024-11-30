package com.example.freevideogame.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.R
import com.example.freevideogame.model.GameListResponse

class GameMainAdapter(private val gamesMain: List<GameListResponse>, private val onItemSelected:(Int) -> Unit): RecyclerView.Adapter<GameMainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameMainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_game_main, parent, false)
        return GameMainViewHolder(layoutInflater)
    }

    override fun getItemCount() = gamesMain.size

    override fun onBindViewHolder(holder: GameMainViewHolder, position: Int) {
        val game = gamesMain[position]
        holder.bind(game, onItemSelected)
    }
}