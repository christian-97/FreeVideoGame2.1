package com.example.freevideogame.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freevideogame.R
import com.example.freevideogame.model.GameDetailsResponse

class FavoriteAdapter(private val games: List<GameDetailsResponse>, private val onItemSelected:(Int) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: View = itemView
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
        val publisher: TextView = itemView.findViewById(R.id.tvPublisher)
        val releaseDate: TextView = itemView.findViewById(R.id.tvReleaseDate)
        val genre: TextView = itemView.findViewById(R.id.tvGenre)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val imageView2: ImageView = itemView.findViewById(R.id.imageView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val game = games[position]
        holder.title.text = game.title
        holder.description.text = game.description
        holder.publisher.text = game.publisher
        holder.releaseDate.text = "Release Date: " + game.release_date
        holder.genre.text = game.genre

        if (game.platform == "Windows") holder.imageView2.setImageResource(R.drawable.windows)
        else holder.imageView2.setImageResource(R.drawable.icon_browser)

        Glide.with(holder.itemView.context)
            .load(game.thumbnail)
            .into(holder.imageView)

        holder.root.setOnClickListener {
            onItemSelected(game.id)
        }
    }

    override fun getItemCount(): Int = games.size
}

