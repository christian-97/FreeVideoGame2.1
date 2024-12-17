package com.example.freevideogame

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.freevideogame.adapter.SliderWelcomeAdapter
import com.example.freevideogame.databinding.ActivityBelcomeBinding
import com.example.freevideogame.model.ListSlider
import com.example.freevideogame.model.ListSliderWelcome

class BelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBelcomeBinding

    private val sliderHandler = Handler()
    private val sliderRunnable = object : Runnable {
        override fun run() {
            val nextItem = binding.viewPager.currentItem + 1
            binding.viewPager.currentItem = nextItem
            sliderHandler.postDelayed(this, 2000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SystemBarsUtil.hideSystemBars(this)

        // ----------------------------------------------------------------
        initBanner()
        binding.viewPager.isUserInputEnabled = false
        // ----------------------------------------------------------------

        binding.imageContainer.post {
            val containerWidth = binding.imageContainer.width
            setupHorizontalAnimation(
                binding.imageView1,
                binding.imageView2,
                containerWidth,
                isLeftToRight = false
            )
        }

        binding.imageContainer1.post {
            val containerWidth = binding.imageContainer1.width
            setupHorizontalAnimation(
                binding.imageView3,
                binding.imageView4,
                containerWidth,
                isLeftToRight = true
            )
        }


        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


    private fun setupHorizontalAnimation(
        image1: ImageView,
        image2: ImageView,
        containerWidth: Int,
        isLeftToRight: Boolean
    ) {
        if (isLeftToRight) {
            image1.translationX = -containerWidth.toFloat()
            image2.translationX = 0f
        } else {
            image1.translationX = 0f
            image2.translationX = containerWidth.toFloat()
        }

        val animator = ValueAnimator.ofFloat(0f, containerWidth.toFloat())
        animator.duration = 5000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = null

        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float

            if (isLeftToRight) {
                image1.translationX = value - containerWidth
                image2.translationX = value

                if (image1.translationX >= containerWidth) {
                    image1.translationX = -containerWidth.toFloat() + (value % containerWidth)
                }
                if (image2.translationX >= containerWidth) {
                    image2.translationX = -containerWidth.toFloat() + (value % containerWidth)
                }
            } else {
                image1.translationX = -value
                image2.translationX = containerWidth - value

                if (image1.translationX <= -containerWidth) {
                    image1.translationX = containerWidth.toFloat() - (value % containerWidth)
                }
                if (image2.translationX <= -containerWidth) {
                    image2.translationX = containerWidth.toFloat() - (value % containerWidth)
                }
            }
        }
        animator.start()
    }

    // ----------------------------------------------------------------

    private fun initBanner() {
        val adapter = SliderWelcomeAdapter(ListSliderWelcome.slider, binding.viewPager)
        binding.viewPager.adapter = adapter
        binding.viewPager.clipToPadding = false
        binding.viewPager.clipChildren = false
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer(ViewPager2.PageTransformer { page, position ->
                val r = 1 - Math.abs(position)
                page.scaleX = 0.85f + r * 0.22f
            })
        }
        binding.viewPager.setPageTransformer(compositePageTransformer)

        binding.viewPager.setCurrentItem(Int.MAX_VALUE / 2, false)
        binding.viewPager.currentItem = 1
        binding.viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks((sliderRunnable))
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
    }
}