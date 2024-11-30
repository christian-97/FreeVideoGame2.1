package com.example.freevideogame.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentResultOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.freevideogame.R
import com.example.freevideogame.model.GameListResponse
import com.squareup.picasso.Picasso


class ViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val fragments = listOf(
        StartFragment(),
        NewFragment()
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getFragment(position: Int): Fragment {
        return fragments[position]
    }
}