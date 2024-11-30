package com.example.freevideogame.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.freevideogame.R
import com.example.freevideogame.adapter.GameAdapter
import com.example.freevideogame.adapter.ImageAdapter
import com.example.freevideogame.retrofit.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class CategoryFragment : Fragment(R.layout.fragment_category) {
    private lateinit var viewPager: ViewPager2
    private lateinit var handler: Handler
    private var imageList = arrayListOf<Int>()
    private lateinit var adapter: ImageAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewPager = view.findViewById(R.id.viewPager)

        //init()
        //setUpTransformer()

        /*viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })*/
    }

    /*override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }

    private val runnable = Runnable {
        viewPager.currentItem = (viewPager.currentItem + 1) % imageList.size
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(41))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager.setPageTransformer(transformer)
    }

    private fun init() {
        handler = Handler(Looper.getMainLooper())
        imageList.add(R.drawable.fondo)
        imageList.add(R.drawable.main)
        imageList.add(R.drawable.header_bg)

        adapter = ImageAdapter(imageList, viewPager)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }*/
}