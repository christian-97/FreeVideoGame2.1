package com.example.freevideogame.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.freevideogame.GameDetailsActivity
import com.example.freevideogame.R
import com.example.freevideogame.adapter.GameAdapter
import com.example.freevideogame.databinding.FragmentStartBinding
import com.example.freevideogame.retrofit.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private  val retrofit = RetrofitHelper.getInstace()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gamesList()
    }

    private fun gamesList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getGames()
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
}