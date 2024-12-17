package com.example.freevideogame.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.freevideogame.databinding.ViewholderWelcomeBinding
import com.example.freevideogame.model.SliderWelcome

class SliderWelcomeAdapter(private var sliderItems: MutableList<SliderWelcome>, private  val viewPager2: ViewPager2): RecyclerView.Adapter<SliderWelcomeAdapter.SliderViewHolder>() {
    private var context: Context?= null

    inner class SliderViewHolder(private val binding: ViewholderWelcomeBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(sliderItem: SliderWelcome) {
            val requestOptions = RequestOptions().transform(FitCenter(), RoundedCorners(60))
            context?.let {
                Glide.with(it).load(sliderItem.image).apply { requestOptions }
                    .into(binding.imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        context = parent.context
        val binding = ViewholderWelcomeBinding.inflate(LayoutInflater.from(context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val actualPosition = position % sliderItems.size
        holder.bind(sliderItems[actualPosition])
    }

    override fun getItemCount() = Integer.MAX_VALUE
}