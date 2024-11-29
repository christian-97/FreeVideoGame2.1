package com.example.freevideogame.model

data class GameDetailsResponse (
    val title : String,
    val thumbnail: String,
    val status: String,
    val short_description: String,
    val description: String,
    val game_url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val release_date: String,
    val freetogame_profile_url: String,

    val minimum_system_requirements: Requirements?,
    val screenshots: List<Screenshots>
)

data class Requirements(
    val os: String,
    val processor: String,
    val memory: String,
    val graphics: String,
    val storage: String,
)

data class Screenshots(
    val image: String
)