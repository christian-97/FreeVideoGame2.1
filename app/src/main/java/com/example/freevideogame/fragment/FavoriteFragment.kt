package com.example.freevideogame.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freevideogame.GameDetailsActivity
import com.example.freevideogame.R
import com.example.freevideogame.adapter.FavoriteAdapter
import com.example.freevideogame.model.GameDetailsResponse
import com.example.freevideogame.retrofit.RetrofitHelper
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class FavoriteFragment : Fragment() {
    val db = Firebase.firestore
    private var retrofit = RetrofitHelper.getInstace()
    private lateinit var idList : MutableList<Int>

    private lateinit var rvFavorite: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)

        rvFavorite = rootView.findViewById(R.id.rvFavorite)
        progressBar = rootView.findViewById(R.id.progressBar)

        idList = mutableListOf()

        init()
        return rootView
    }

    private fun init() {
        val shared = requireContext().getSharedPreferences("LoginData", Context.MODE_PRIVATE)
        val email = shared.getString("email", "").toString()

        db.collection("Favorite")
            .get()
            .addOnSuccessListener { result ->
                val idList = mutableListOf<Int>()
                for (document in result) {
                    val mail = document.get("email").toString()
                    val id = document.get("id").toString().toInt()
                    if (email == mail) {
                        idList.add(id)
                    }
                }
                fetchFavoriteGames(idList)
            }

        rvFavorite.layoutManager = LinearLayoutManager(context)
    }

    private fun fetchFavoriteGames(idList: List<Int>) {
        val favoriteGames = mutableListOf<GameDetailsResponse>()
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            idList.forEach { gameId ->
                val response = retrofit.getGameDetails(gameId)
                if (response.isSuccessful) {
                    response.body()?.let { favoriteGames.add(it) }
                }
            }

            rvFavorite.adapter = FavoriteAdapter(favoriteGames) { navigationDetails(it) }
            progressBar.visibility = View.GONE
        }
    }

    private fun navigationDetails(id: Int) {
        val intent = Intent(context, GameDetailsActivity::class.java)
        intent.putExtra("extra_id", id)
        startActivity(intent)
    }
}
