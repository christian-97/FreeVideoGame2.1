package com.example.freevideogame.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.freevideogame.databinding.ViewholderRecommendBinding
import com.example.freevideogame.model.SliderGameRecommended

class SliderRecommend(private val items: MutableList<SliderGameRecommended>): RecyclerView.Adapter<SliderRecommend.ViewHolder>() {

    private var context: Context?= null

    inner class ViewHolder(private val binding: ViewholderRecommendBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SliderGameRecommended) {
            val requestOptions = RequestOptions()
                .transform(FitCenter())
            Glide.with(context!!).load(item.image).apply(requestOptions).into(binding.imageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderRecommend.ViewHolder {
        context = parent.context
        val binding = ViewholderRecommendBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderRecommend.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}