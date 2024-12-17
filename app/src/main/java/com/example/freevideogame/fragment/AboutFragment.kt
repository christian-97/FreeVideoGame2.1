package com.example.freevideogame.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.freevideogame.R


class AboutFragment : Fragment(R.layout.fragment_about) {
    private lateinit var btnExpand: TextView
    private lateinit var cardDescription: CardView
    private lateinit var tvDescription: TextView

    private lateinit var btnExpand2: TextView
    private lateinit var cardDescription2: CardView
    private lateinit var tvDescription2: TextView

    private lateinit var btnExpand3: TextView
    private lateinit var cardDescription3: CardView
    private lateinit var tvDescription3: TextView

    private lateinit var btnExpand4: TextView
    private lateinit var cardDescription4: CardView
    private lateinit var tvDescription4: TextView

    private lateinit var textTitle: TextView

    private var isExpanded1 = false
    private var isExpanded2 = false
    private var isExpanded3 = false
    private var isExpanded4 = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnExpand = view.findViewById(R.id.btnExpand)
        cardDescription = view.findViewById(R.id.cardDescription)
        tvDescription = view.findViewById(R.id.tvDescription)


        btnExpand2 = view.findViewById(R.id.btnExpand2)
        cardDescription2 = view.findViewById(R.id.cardDescription2)
        tvDescription2 = view.findViewById(R.id.tvDescription2)

        btnExpand3 = view.findViewById(R.id.btnExpand3)
        cardDescription3 = view.findViewById(R.id.cardDescription3)
        tvDescription3 = view.findViewById(R.id.tvDescription3)

        btnExpand4 = view.findViewById(R.id.btnExpand4)
        cardDescription4 = view.findViewById(R.id.cardDescription4)
        tvDescription4 = view.findViewById(R.id.tvDescription4)

        textTitle = view.findViewById(R.id.textTitle)
        init()
    }

    private fun init() {

        btnExpand.setOnClickListener {
            if (isExpanded1) {
                collapseCard(cardDescription)
            } else {
                expandCard(cardDescription)
            }
            isExpanded1 = !isExpanded1
        }

        btnExpand2.setOnClickListener {
            if (isExpanded2) {
                collapseCard(cardDescription2)
            } else {
                expandCard(cardDescription2)
            }
            isExpanded2 = !isExpanded2
        }

        btnExpand3.setOnClickListener {
            if (isExpanded3) {
                collapseCard(cardDescription3)
            } else {
                expandCard(cardDescription3)
            }
            isExpanded3 = !isExpanded3
        }

        btnExpand4.setOnClickListener {
            if (isExpanded4) {
                collapseCard(cardDescription4)
            } else {
                expandCard(cardDescription4)
            }
            isExpanded4 = !isExpanded4
        }


        // ----------------------------------------------------------------
        val spannableString = SpannableString(textTitle.text.toString())

        val color = context?.let { ContextCompat.getColor(it, R.color.blue) }
        spannableString.setSpan(color?.let { ForegroundColorSpan(it) }, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(color?.let { ForegroundColorSpan(it) }, 5, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(color?.let { ForegroundColorSpan(it) }, 11, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textTitle.text = spannableString
        // ----------------------------------------------------------------
    }

    private fun expandCard(card: CardView) {
        card.visibility = View.VISIBLE
        card.alpha = 0f
        card.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    card.visibility = View.VISIBLE
                }
            })
    }

    private fun collapseCard(card: CardView) {
        card.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    card.visibility = View.GONE
                }
            })
    }
}