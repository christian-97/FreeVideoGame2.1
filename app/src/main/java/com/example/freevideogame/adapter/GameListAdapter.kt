package com.example.freevideogame.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.freevideogame.databinding.ViewholderGameBinding
import com.example.freevideogame.model.GameListResponse

class GameListAdapter(private val items: List<GameListResponse>): RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

    private var context: Context ?= null

    inner class ViewHolder(private val binding: ViewholderGameBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameListResponse) {
            val requestOptions = RequestOptions()
                .transform(FitCenter(), RoundedCorners(30))
            Glide.with(context!!).load(item.thumbnail).apply(requestOptions).into(binding.pic)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderGameBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameListAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}