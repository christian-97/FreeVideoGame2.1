package com.example.freevideogame.service

import com.example.freevideogame.model.GameDetailsResponse
import com.example.freevideogame.model.GameList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getGames(): Response<GameList>

    @GET("game")
    suspend fun getGameDetails(@Query("id") gameId: Int): Response<GameDetailsResponse>


    @GET("games")
    suspend fun getGamePlatform(@Query("platform") platform: String): Response<GameList>

    @GET("games")
    suspend fun getGameCategory(@Query("category") category: String): Response<GameList>
}