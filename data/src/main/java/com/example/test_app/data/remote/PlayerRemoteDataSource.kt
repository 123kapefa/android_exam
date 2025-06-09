package com.example.test_app.data.remote

import com.example.test_app.data.remote.dto.AchievementSchemaDto
import com.example.test_app.data.remote.dto.PlayerAchievementDto
import com.example.test_app.data.remote.dto.PlayerDto

interface PlayerRemoteDataSource {
    suspend fun fetchAchievementSchema(): List<AchievementSchemaDto>
    suspend fun fetchPlayerAchievements(steamId: String): List<PlayerAchievementDto>
    suspend fun fetchPlayerInfo(steamId: String): PlayerDto
}