package com.example.freevideogame.model

data class GameListResponse (
    val id: Int,
    val thumbnail: String,
    val title: String,
    val genre: String,
    val release_date: String,
    val platform: String,
    val short_description: String,
    val publisher: String,
)

typealias GameList = List<GameListResponse>
