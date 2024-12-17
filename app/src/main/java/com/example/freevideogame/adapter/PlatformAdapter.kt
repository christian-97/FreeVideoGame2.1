package com.example.freevideogame.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.freevideogame.R
import com.example.freevideogame.databinding.LayoutPlatformBinding
import com.example.freevideogame.model.GameListResponse


class PlatformAdapter(private val context: Context, private val platform: List<GameListResponse>, private val onItemSelected:(Int) -> Unit): RecyclerView.Adapter<PlatformAdapter.PlatformViewHolder>() {

    class PlatformViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = LayoutPlatformBinding.bind(view)
        fun bind(platform: GameListResponse, onItemSelected:(Int) -> Unit) {
            binding.tvTitle.text = platform.title
            binding.tvDescription.text = platform.short_description
            binding.tvPublisher.text = platform.publisher
            binding.tvRealaseDate.text = "Release Date: " + platform.release_date
            binding.tvGenre.text = platform.genre

            if (platform.platform == "PC (Windows)") binding.icon.setImageResource(R.drawable.windows)
            else binding.icon.setImageResource(R.drawable.icon_browser)

            val requestOptions = RequestOptions()
                .transform(FitCenter(), RoundedCorners(20))
            Glide.with(itemView.context).load(platform.thumbnail).apply(requestOptions).into(binding.imageView)

            binding.root.setOnClickListener {
                onItemSelected(platform.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformViewHolder {
        val layoutInflater = LayoutInflater.from(context).inflate(R.layout.layout_platform, parent, false)
        return PlatformViewHolder(layoutInflater)
    }

    override fun getItemCount() = platform.size

    override fun onBindViewHolder(holder: PlatformViewHolder, position: Int) {
        holder.bind(platform[position], onItemSelected)
    }
}