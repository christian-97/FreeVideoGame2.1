package com.example.freevideogame.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.freevideogame.R

class ImageAdapter(private val imageList: ArrayList<Int>, private val viewPager: ViewPager2): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        return ImageViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.image.setImageResource(imageList[position % imageList.size])

        if (position == imageList.size-1) {
            viewPager.post(runnable)
        }
    }

    override fun getItemCount() = imageList.size

    class ImageViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val image : ImageView = view.findViewById(R.id.ivImage)
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}

