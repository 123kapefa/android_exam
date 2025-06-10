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


    /** Информация о игроке всегда берётся из сети. */
    override suspend fun getPlayerInfo(steamId: String): Player =
        withContext(Dispatchers.IO) {
            val player = remote.fetchPlayerInfo(steamId).toDomain()
            playerDao.insert(player.toEntity())
            player
        }

    /**
     * Список достижений:
     * 1) Пытаемся из Room-кэша.
     * 2) Если кэш пуст — тянем из сети,
     *    объединяем схему + прогресс, сохраняем в БД.
     */
    override suspend fun getPlayerAchievements(steamId: String): List<PlayerAchievement> =
        withContext(Dispatchers.IO) {
            // 1. КЭШ
            val cached = dao.getAll().map { it.toDomain() }
            if (cached.isNotEmpty()) return@withContext cached

            // 2. СЕТЬ
            val schema   = remote.fetchAchievementSchema().map { it.toDomain() }
            val progress = remote.fetchPlayerAchievements(steamId)

            val merged = schema.map { ach ->
                progress.find { it.id == ach.id }
                    ?.let { ach.updateWithPlayerData(it) }
                    ?: ach
            }

            // 3. СОХРАНЯЕМ
            dao.insertAll(merged.map { it.toEntity() })
            merged
        }

    /** Чистая схема (без прогресса) — тоже в IO-потоке. */
    override suspend fun getAchievementSchema(): List<PlayerAchievement> =
        withContext(Dispatchers.IO) {
            remote.fetchAchievementSchema().map { it.toDomain() }
        }
}