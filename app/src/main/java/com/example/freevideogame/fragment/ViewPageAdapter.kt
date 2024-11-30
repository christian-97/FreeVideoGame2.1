package com.example.freevideogame.fragment


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter



class ViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val fragments = listOf(
        StartFragment(),
        CategoryFragment()
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getFragment(position: Int): Fragment {
        return fragments[position]
    }
}