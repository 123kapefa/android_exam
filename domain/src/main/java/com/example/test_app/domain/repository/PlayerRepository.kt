package com.example.test_app.domain.repository

import com.example.test_app.domain.model.Player
import com.example.test_app.domain.model.PlayerAchievement

interface PlayerRepository {
    suspend fun getSavedSteamId(): String?
    suspend fun getPlayerInfo(steamId: String): Player
    suspend fun getPlayerAchievements(steamId: String): List<PlayerAchievement>
    suspend fun getAchievementSchema(): List<PlayerAchievement>
    suspend fun getAchievementById(id: String): PlayerAchievement
    suspend fun logout()
}