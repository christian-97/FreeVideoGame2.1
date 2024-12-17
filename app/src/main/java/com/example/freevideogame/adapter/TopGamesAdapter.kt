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
import com.example.freevideogame.databinding.ViewholderTopGamesBinding
import com.example.freevideogame.model.SliderTopGames

class TopGamesAdapter(private val items: MutableList<SliderTopGames>): RecyclerView.Adapter<TopGamesAdapter.ViewHolder>() {

    private var context: Context?= null

    inner class ViewHolder(private val binding: ViewholderTopGamesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SliderTopGames) {
            binding.nameText.text = item.title
            val requestOptions = RequestOptions()
                .transform(FitCenter(), RoundedCorners(30))
            Glide.with(context!!).load(item.image).apply(requestOptions).into(binding.pic)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopGamesAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderTopGamesBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopGamesAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}