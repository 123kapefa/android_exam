package com.example.test_app.domain.usecase

import com.example.test_app.domain.repository.PlayerRepository

class GetPlayerInfoUseCase(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(steamId: String) = repository.getPlayerInfo(steamId)
}