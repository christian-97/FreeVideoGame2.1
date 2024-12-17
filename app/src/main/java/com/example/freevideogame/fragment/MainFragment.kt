package com.example.freevideogame.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.freevideogame.GameDetailsActivity
import com.example.freevideogame.R
import com.example.freevideogame.adapter.Adapter
import com.example.freevideogame.adapter.CategoryIdAdapter
import com.example.freevideogame.adapter.GameAdapter
import com.example.freevideogame.adapter.GameListAdapter
import com.example.freevideogame.adapter.GameMainAdapter
import com.example.freevideogame.adapter.PopularDialogAdapter
import com.example.freevideogame.adapter.SliderAdapter
import com.example.freevideogame.adapter.SliderRecommend
import com.example.freevideogame.adapter.TopDialogAdapter
import com.example.freevideogame.adapter.TopGamesAdapter
import com.example.freevideogame.model.ListSlider
import com.example.freevideogame.model.ListSliderPopularGames
import com.example.freevideogame.model.ListSliderRecommended
import com.example.freevideogame.retrofit.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment: Fragment(R.layout.fragment_main) {
    private lateinit var viewPager2: ViewPager2
    private lateinit var progressBarSlider: ProgressBar
    private lateinit var progressBarTopGames: ProgressBar
    private lateinit var progressBarPopularGames: ProgressBar
    private lateinit var progressBarSuperhero: ProgressBar
    private lateinit var progressBarTank: ProgressBar
    private lateinit var progressBarSpace: ProgressBar
    private lateinit var progressBarAction: ProgressBar

    private lateinit var recyclerViewTopGames: RecyclerView
    private lateinit var recyclerViewPopularGames: RecyclerView
    private lateinit var recyclerViewSuperhero: RecyclerView
    private lateinit var recyclerViewTank: RecyclerView
    private lateinit var recyclerViewSpace: RecyclerView
    private lateinit var recyclerViewAction: RecyclerView


    private lateinit var rvRecommended: RecyclerView

    private lateinit var tvTop: TextView
    private lateinit var tvPopular: TextView

    private val retrofit = RetrofitHelper.getInstace()
    private val sliderHandler = Handler()
    private val sliderRunnable = object : Runnable {
        override fun run() {
            val nextItem = (viewPager2.currentItem + 1) % ListSlider.slider.size
            viewPager2.currentItem = nextItem

            sliderHandler.postDelayed(this, 2000)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager2 = view.findViewById(R.id.viewPager2)

        progressBarSlider = view.findViewById(R.id.progressBarSlider)
        progressBarTopGames = view.findViewById(R.id.progressBarTopGames)
        progressBarPopularGames = view.findViewById(R.id.progressBarPopularGames)
        progressBarSuperhero = view.findViewById(R.id.progressBarSuperhero)
        progressBarTank = view.findViewById(R.id.progressBarTank)
        progressBarSpace = view.findViewById(R.id.progressBarSpace)
        progressBarAction = view.findViewById(R.id.progressBarAction)

        recyclerViewTopGames = view.findViewById(R.id.recyclerViewTopGames)
        recyclerViewPopularGames = view.findViewById(R.id.recyclerViewPopularGames)
        recyclerViewSuperhero = view.findViewById(R.id.recyclerViewSuperhero)
        recyclerViewTank = view.findViewById(R.id.recyclerViewTank)
        recyclerViewSpace = view.findViewById(R.id.recyclerViewSpace)
        recyclerViewAction = view.findViewById(R.id.recyclerViewAction)

        rvRecommended = view.findViewById(R.id.rvRecommended)

        tvTop = view.findViewById(R.id.tvTop)
        tvPopular = view.findViewById(R.id.tvPopular)

        viewPager2.isUserInputEnabled = false
        initBanner()
        initTopGame()
        initPopularGames()
        initRecommend()
        initSuperhero()
        initTank()
        initSpace()
        initAction()

        tvTop.setOnClickListener { showDialog( )}
        tvPopular.setOnClickListener { showDialogPopular()}
    }

    private fun showDialogPopular() {
        context?.let { ctx ->
            val dialog = Dialog(ctx)
            dialog.setContentView(R.layout.dialog_image)

            val rvTop: RecyclerView = dialog.findViewById(R.id.rvTop)
            val progressBar: ProgressBar = dialog.findViewById(R.id.progressBar1)

            if (::progressBarPopularGames.isInitialized) progressBar.isVisible = true
            CoroutineScope(Dispatchers.IO).launch {
                val response = retrofit.getGameSortBy("popularity")
                val games = response.body()
                if (games != null) {
                    withContext(Dispatchers.Main) {
                        rvTop.layoutManager = LinearLayoutManager(context)
                        rvTop.adapter = PopularDialogAdapter(games)
                        progressBar.isVisible = false
                    }
                }
            }

            dialog.show()
        }
    }

    private fun showDialog() {
        context?.let { ctx ->
            val dialog = Dialog(ctx)
            dialog.setContentView(R.layout.dialog_image)

            val rvTop: RecyclerView = dialog.findViewById(R.id.rvTop)
            rvTop.layoutManager = LinearLayoutManager(context)
            rvTop.adapter = TopDialogAdapter(ListSliderPopularGames.slider)
            dialog.show()
        }
    }


    private fun initRecommend() {
        rvRecommended.layoutManager = GridLayoutManager(context, 2)
        rvRecommended.adapter = SliderRecommend(ListSliderRecommended.slider)
    }

    private fun initTopGame() {
        recyclerViewTopGames.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewTopGames.adapter = TopGamesAdapter(ListSliderPopularGames.slider)
        progressBarTopGames.isVisible = false
    }

    private fun initPopularGames() {
        if (::progressBarPopularGames.isInitialized) progressBarPopularGames.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGameSortBy("popularity")
            val games = response.body()
            if (games != null) {
                withContext(Dispatchers.Main) {
                    recyclerViewPopularGames.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewPopularGames.adapter = GameListAdapter(games)
                    progressBarPopularGames.isVisible = false
                }
            }
        }
    }

    private fun initBanner(){
        progressBarSlider.visibility = View.VISIBLE
        viewPager2.adapter = SliderAdapter(ListSlider.slider, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        progressBarSlider.visibility = View.GONE

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer(ViewPager2.PageTransformer {page, position ->
                val r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            })
        }

        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.currentItem = 1
        viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
            }
        })
    }

    // ----------------------------------------------------------------
    private fun initSuperhero() {
        progressBarSuperhero.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGameCategory("superhero")
            val games = response.body()

            if (games != null) {
                withContext(Dispatchers.Main) {
                    recyclerViewSuperhero.layoutManager = LinearLayoutManager(context)
                    recyclerViewSuperhero.adapter = context?.let { CategoryIdAdapter(it, games) { navigationDetails(it) } }
                    progressBarSuperhero.isVisible = false
                }
            }

        }
    }

    private fun navigationDetails(id: Int) {
        val intent = Intent(context, GameDetailsActivity::class.java)
        intent.putExtra("extra_id", id)
        startActivity(intent)
    }

    private fun initTank() {
        progressBarTank.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGameCategory("tank")
            val games = response.body()

            if (games != null) {
                withContext(Dispatchers.Main) {
                    recyclerViewTank.layoutManager = LinearLayoutManager(context)
                    recyclerViewTank.adapter = GameMainAdapter(games) { navigationDetails(it) }
                    progressBarTank.isVisible = false
                }
            }

        }
    }

    private fun initSpace() {
        progressBarSpace.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGameCategory("space")
            val games = response.body()

            if (games != null) {
                withContext(Dispatchers.Main) {
                    recyclerViewSpace.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewSpace.adapter = Adapter(games) { navigationDetails(it) }
                    progressBarSpace.isVisible = false
                }
            }

        }
    }

    private fun initAction() {
        progressBarAction.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGameCategory("action")
            val games = response.body()

            if (games != null) {
                withContext(Dispatchers.Main) {
                    recyclerViewAction.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewAction.adapter = Adapter(games) { navigationDetails(it) }
                    progressBarAction.isVisible = false
                }
            }

        }
    }
    // ----------------------------------------------------------------



    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks((sliderRunnable))
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
    }
}