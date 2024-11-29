package com.example.freevideogame.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.R

class MainBarAdapter(private val menu: List<String>, private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<MainBarViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_main_bar, parent, false)
        return MainBarViewHolder(layoutInflater)
    }

    override fun getItemCount() = menu.size

    override fun onBindViewHolder(holder: MainBarViewHolder, position: Int) {
        holder.bind(menu[position], position == selectedPosition)

        holder.itemView.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition == RecyclerView.NO_POSITION) return@setOnClickListener

            val previousPosition = selectedPosition
            selectedPosition = currentPosition

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            onItemClick(position)
        }
    }
}