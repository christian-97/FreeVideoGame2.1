package com.example.freevideogame.fragment


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter



class ViewPagerAdapter(activity: StartFragment): FragmentStateAdapter(activity) {

    private val fragments = listOf(
        GamesFragment(),
        CategoryFragment(),
        PlatformFragment(),
        RelevanceFragment(),
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getFragment(position: Int): Fragment {
        return fragments[position]
    }
}