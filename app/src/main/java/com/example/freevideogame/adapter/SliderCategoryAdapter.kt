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
import com.example.freevideogame.model.SliderCategory
import com.example.freevideogame.adapter.SliderCategoryAdapter.SliderCategoryViewHolder
import com.example.freevideogame.databinding.LayoutCategoryBinding

class SliderCategoryAdapter(private val context: Context, private val category: MutableList<SliderCategory>, private val onItemSelected:(String) -> Unit): RecyclerView.Adapter<SliderCategoryViewHolder>() {

    class SliderCategoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = LayoutCategoryBinding.bind(view)
        fun bind(category: SliderCategory, onItemSelected:(String) -> Unit) {
            binding.tvTitle.text = category.title

            val requestOptions = RequestOptions()
                .transform(FitCenter(), RoundedCorners(30))
            Glide.with(itemView.context).load(category.image).apply(requestOptions).into(binding.ivCategory)

            // ----------------------------------------------------------------
            val category = when (category.title) {
                "MMORPG" -> "mmorpg"; "PvP" -> "pvp"
                "Shooter" -> "shooter"; "PvE" -> "pve"
                "MOBA" -> "moba"; "Pixel" -> "pixel"
                "Anime" -> "anime"; "MMORTS" -> "mmorts"
                "Battle Royale" -> "battle-royale"; "Action" -> "action"
                "Strategy" -> "strategy"; "Voxel" -> "voxel"
                "Fantasy" -> "fantasy"; "Zombie" -> "zombie"
                "Sci-Fi" -> "sci-fi"; "Turn-Based" -> "turn-based"
                "Card Games" -> "card"; "First Person" -> "first-person"
                "Racing" -> "racing"; "Third Person" -> "third-Person"
                "Fighting" -> "fighting"; "Top-Down" -> "top-down"
                "Social" -> "social"; "3D Graphics" -> "3d"
                "Sports" -> "sports"; "2D Graphics" -> "2d"
                "MMO" -> "mmo"; "Tank" -> "tank"
                "MMOFPS" -> "mmofps"; "Space" -> "space"
                "Action RPG" -> "action-rpg"; "Sailing" -> "sailing"
                "Sandbox" -> "sandbox"; "3Side Scroller" -> "side-scroller"
                "Open World" -> "open-world"; "Superhero" -> "superhero"
                "Survival" -> "survival"; "Permadeath" -> "permadeath"
                "MMOTPS" -> "mmotps"
                else -> "other"
            }

            binding.root.setOnClickListener {
                onItemSelected(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(context).inflate(R.layout.layout_category, parent, false)
        return SliderCategoryViewHolder(layoutInflater)
    }

    override fun getItemCount() = category.size

    override fun onBindViewHolder(holder: SliderCategoryViewHolder, position: Int) {
        holder.bind(category[position], onItemSelected)
    }
}