package com.example.freevideogame.fragment

import android.content.Intent
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
import com.example.freevideogame.GameDetailsActivity
import com.example.freevideogame.R
import com.example.freevideogame.SecessionActivity
import com.example.freevideogame.adapter.GameAdapter
import com.example.freevideogame.adapter.SliderCategoryAdapter
import com.example.freevideogame.adapter.SliderRecommend
import com.example.freevideogame.model.ListSlider
import com.example.freevideogame.model.ListSliderCategory
import com.example.freevideogame.model.ListSliderRecommended
import com.example.freevideogame.model.SliderCategory
import com.example.freevideogame.retrofit.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private lateinit var rvCategory: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCategory = view.findViewById(R.id.rvCategory)

        categoryList()
    }

    private fun categoryList() {
        rvCategory.layoutManager = GridLayoutManager(context, 2)
        rvCategory.adapter = context?.let { SliderCategoryAdapter(it, ListSliderCategory.slider) {navigationCategory(it)} }
    }

    private fun navigationCategory(category: String) {
        val intent = Intent(context, SecessionActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}