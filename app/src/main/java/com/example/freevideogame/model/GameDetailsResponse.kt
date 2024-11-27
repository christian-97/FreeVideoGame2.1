package com.example.freevideogame.model

data class GameDetailsResponse (
    val title : String,
    val thumbnail: String,
    val status: String,
    val short_description: String,
    val description: String,
    val game_url: String
)