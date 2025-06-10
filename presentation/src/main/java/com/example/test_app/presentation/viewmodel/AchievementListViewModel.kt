package com.example.test_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_app.domain.model.PlayerAchievement
import com.example.test_app.domain.usecase.GetPlayerAchievementsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AchievementListUiState {
    object Loading : AchievementListUiState()
    data class Success(val data: List<PlayerAchievement>) : AchievementListUiState()
    data class Error(val message: String) : AchievementListUiState()
}

class AchievementListViewModel(
    private val getPlayerAchievementsUseCase: GetPlayerAchievementsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AchievementListUiState>(AchievementListUiState.Loading)
    val uiState: StateFlow<AchievementListUiState> get() = _uiState

    fun loadPlayerAchievements(steamId: String) {
        viewModelScope.launch {
            _uiState.value = AchievementListUiState.Loading
            try {
                val data = getPlayerAchievementsUseCase(steamId)
                _uiState.value = AchievementListUiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = AchievementListUiState.Error("Не удалось загрузить достижения: ${e.message}")
            }
        }
    }


}