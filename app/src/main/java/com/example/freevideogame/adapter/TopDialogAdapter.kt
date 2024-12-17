package com.example.freevideogame.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.freevideogame.databinding.ViewholderDialogTopBinding
import com.example.freevideogame.model.SliderTopGames

class TopDialogAdapter(private val items: MutableList<SliderTopGames>): RecyclerView.Adapter<TopDialogAdapter.ViewHolder>() {

    private var context: Context?= null

    inner class ViewHolder(private val binding: ViewholderDialogTopBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SliderTopGames) {
            binding.name.text = item.title
            val requestOptions = RequestOptions()
                .transform(FitCenter())
            Glide.with(context!!).load(item.image).apply(requestOptions).into(binding.iv)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopDialogAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderDialogTopBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopDialogAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}