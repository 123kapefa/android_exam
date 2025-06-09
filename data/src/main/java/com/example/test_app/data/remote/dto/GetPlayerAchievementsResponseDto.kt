package com.example.test_app.data.remote.dto

data class GetPlayerAchievementsResponseDto(
    val playerstats: PlayerStatsDto
)

data class PlayerStatsDto(
    val steamID: String,
    val gameName: String,
    val achievements: List<PlayerAchievementDto>,
    val success: Boolean
)