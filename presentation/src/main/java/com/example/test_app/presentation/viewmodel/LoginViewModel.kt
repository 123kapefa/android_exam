package com.example.test_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.test_app.domain.usecase.GetPlayerAchievementsUseCase
import com.example.test_app.domain.usecase.GetPlayerInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val getPlayerInfo: GetPlayerInfoUseCase,
    private val getAchievements: GetPlayerAchievementsUseCase
) : ViewModel() {

    suspend fun login(steamId: String) = withContext(Dispatchers.IO) {
        getPlayerInfo(steamId)

        delay(1500)

        getAchievements(steamId)
    }
}