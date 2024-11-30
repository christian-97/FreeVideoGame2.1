package com.example.freevideogame.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private var selectedCategory: String? = null
    private var selectedPlatform: String? = null

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StartFragment()
            1 -> CategoryFragment()
            else -> NewFragment.newInstance(
                selectedCategory ?: "",
                selectedPlatform ?: ""
            )
        }
    }

    fun setSelectedData(category: String?, platform: String?) {
        selectedCategory = category
        selectedPlatform = platform
    }
}