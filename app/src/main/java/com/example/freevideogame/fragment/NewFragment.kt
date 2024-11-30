package com.example.freevideogame.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.freevideogame.GameDetailsActivity
import com.example.freevideogame.adapter.GameAdapter
import com.example.freevideogame.databinding.FragmentNewBinding
import com.example.freevideogame.databinding.FragmentStartBinding
import com.example.freevideogame.retrofit.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewFragment : Fragment() {

    private var _binding: FragmentNewBinding? = null
    private val binding get() = _binding!!

    private val retrofit = RetrofitHelper.getInstace()

    private var selectedCategory: String? = null
    private var selectedPlatform: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            selectedCategory = it.getString("category")
            selectedPlatform = it.getString("platform")
        }
        _binding = FragmentNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!selectedCategory.isNullOrEmpty()) {
            gamesList("category", selectedCategory!!)
        } else if (!selectedPlatform.isNullOrEmpty()) {
            gamesList("platform", selectedPlatform!!)
        } else {
            gamesList("", "")
        }
    }

    private fun gamesList(by: String, selected: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = retrofit
            val response = when {
                selected.isEmpty() -> retrofit.getGames()
                by == "platform" -> retrofit.getGamePlatform(selected)
                else -> retrofit.getGameCategory(selected)
            }

            val games = response.body()
            if (games != null) {
                withContext(Dispatchers.Main) {
                    binding.rvGame.layoutManager = GridLayoutManager(context, 2)
                    binding.rvGame.adapter = GameAdapter(games) { navigationDetails(it) }
                }
            }
        }
    }

    private fun navigationDetails(id: Int) {
        val intent = Intent(context, GameDetailsActivity::class.java)
        intent.putExtra("extra_id", id)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(category: String, platform: String): NewFragment {
            val fragment = NewFragment()
            val args = Bundle().apply {
                putString("category", category)
                putString("platform", platform)
            }
            fragment.arguments = args
            return fragment
        }
    }
}