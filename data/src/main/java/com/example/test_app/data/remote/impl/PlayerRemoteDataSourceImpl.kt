package com.example.test_app.data.remote.impl

import com.example.test_app.data.remote.PlayerRemoteDataSource
import com.example.test_app.data.remote.api.SteamApi
import com.example.test_app.data.remote.dto.AchievementSchemaDto
import com.example.test_app.data.remote.dto.PlayerAchievementDto
import com.example.test_app.data.remote.dto.PlayerDto

class PlayerRemoteDataSourceImpl(
    private val api: SteamApi,
    private val apiKey: String
) : PlayerRemoteDataSource {

    override suspend fun fetchAchievementSchema(): List<AchievementSchemaDto> {
        return api.getSchemaForGame(apiKey).game.availableGameStats.achievements
    }

    override suspend fun fetchPlayerAchievements(steamId: String): List<PlayerAchievementDto> {
        return api.getPlayerAchievements(apiKey, steamId).playerstats.achievements
    }

    override suspend fun fetchPlayerInfo(steamId: String): PlayerDto {
        return api.getPlayerSummaries(apiKey, steamId).response.players.first()
    }
}