package com.example.test_app.domain.usecase

import com.example.test_app.domain.repository.PlayerRepository

class GetPlayerAchievementsUseCase(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(steamId: String) = repository.getPlayerAchievements(steamId)
}