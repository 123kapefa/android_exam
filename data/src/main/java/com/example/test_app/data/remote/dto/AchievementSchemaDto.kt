package com.example.test_app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AchievementSchemaDto(
    @SerializedName("name") val id: String,
    @SerializedName("displayName") val title: String,
    val description: String,
    val hidden: Int,
    val icon: String,
    @SerializedName("icongray") val iconGrayUrl: String
)

data class GetSchemaForGameResponseDto(
    val game: GameSchemaDto
)

data class GameSchemaDto(
    val availableGameStats: GameStatsDto
)

data class GameStatsDto(
    val achievements: List<AchievementSchemaDto>
)