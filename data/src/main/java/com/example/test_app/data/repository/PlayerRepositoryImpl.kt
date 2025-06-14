package com.example.test_app.data.repository

import com.example.test_app.data.mapper.toDomain
import com.example.test_app.data.mapper.updateWithPlayerData
import com.example.test_app.data.remote.PlayerRemoteDataSource
import com.example.test_app.domain.model.Player
import com.example.test_app.domain.model.PlayerAchievement
import com.example.test_app.domain.repository.PlayerRepository

class PlayerRepositoryImpl(
    private val remoteDataSource: PlayerRemoteDataSource
) : PlayerRepository {

    override suspend fun getPlayerInfo(steamId: String): Player {
        return remoteDataSource.fetchPlayerInfo(steamId).toDomain()
    }

    override suspend fun getPlayerAchievements(steamId: String): List<PlayerAchievement> {
        val schema = remoteDataSource.fetchAchievementSchema()
            .map { it.toDomain() }

        val playerProgress = remoteDataSource.fetchPlayerAchievements(steamId)

        return schema.map { achievement ->
            val progress = playerProgress.find { it.id == achievement.id }
            if (progress != null) achievement.updateWithPlayerData(progress)
            else achievement
        }
    }

    override suspend fun getAchievementSchema(): List<PlayerAchievement> {
        return remoteDataSource.fetchAchievementSchema().map { it.toDomain() }
    }
}