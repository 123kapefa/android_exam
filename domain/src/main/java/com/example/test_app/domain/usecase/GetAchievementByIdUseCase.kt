package com.example.test_app.domain.usecase


import com.example.test_app.domain.model.PlayerAchievement
import com.example.test_app.domain.repository.PlayerRepository

class GetAchievementByIdUseCase(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(id: String): PlayerAchievement =
        repository.getAchievementById(id)
}