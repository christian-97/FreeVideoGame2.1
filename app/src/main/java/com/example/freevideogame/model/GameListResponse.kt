package com.example.freevideogame.model

data class GameListResponse (
    val id: Int,
    val thumbnail: String
)

typealias GameList = List<GameListResponse>
