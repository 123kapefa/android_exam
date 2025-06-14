package com.example.test_app.data.repository

import com.example.test_app.data.local.dao.AchievementDao
import com.example.test_app.data.local.dao.PlayerDao
import com.example.test_app.data.mapper.toDomain
import com.example.test_app.data.mapper.toEntity
import com.example.test_app.data.mapper.updateWithPlayerData
import com.example.test_app.data.remote.PlayerRemoteDataSource
import com.example.test_app.domain.model.Player
import com.example.test_app.domain.model.PlayerAchievement
import com.example.test_app.domain.repository.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerRepositoryImpl(
    private val remote: PlayerRemoteDataSource,
    private val dao: AchievementDao,
    private val playerDao: PlayerDao
) : PlayerRepository {


    override suspend fun getSavedSteamId(): String? =
        withContext(Dispatchers.IO) {
            playerDao.getCurrent()?.steamId
        }


    override suspend fun getPlayerInfo(steamId: String): Player =
        withContext(Dispatchers.IO) {
            val player = remote.fetchPlayerInfo(steamId).toDomain()
            playerDao.insert(player.toEntity())
            player
        }


    override suspend fun getPlayerAchievements(steamId: String): List<PlayerAchievement> =
        withContext(Dispatchers.IO) {
            // 1. КЭШ
            val cached = dao.getAll().map { it.toDomain() }
            if (cached.isNotEmpty()) return@withContext cached

            val schema   = remote.fetchAchievementSchema().map { it.toDomain() }
            val progress = remote.fetchPlayerAchievements(steamId)

            val merged = schema.map { ach ->
                progress.find { it.id == ach.id }
                    ?.let { ach.updateWithPlayerData(it) }
                    ?: ach
            }

            dao.insertAll(merged.map { it.toEntity() })
            merged
        }

    override suspend fun getAchievementSchema(): List<PlayerAchievement> =
        withContext(Dispatchers.IO) {
            remote.fetchAchievementSchema().map { it.toDomain() }
        }

    override suspend fun getAchievementById(id: String): PlayerAchievement =
        withContext(Dispatchers.IO) {
            dao.getById(id)?.toDomain() ?: run {
                throw IllegalArgumentException("Achievement $id not found in cache")
            }
        }

    override suspend fun logout() = withContext(Dispatchers.IO) {
        playerDao.clear()
        dao.clear()
    }
}