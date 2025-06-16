package com.example.test_app.presentation.viewmodel

import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_app.domain.model.PlayerAchievement
import com.example.test_app.domain.repository.PlayerRepository
import com.example.test_app.domain.usecase.GetPlayerAchievementsUseCase
import com.example.test_app.presentation.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AchievementListUiState {
    object Loading : AchievementListUiState()
    data class Success(val data: List<PlayerAchievement>) : AchievementListUiState()
    data class Error(val message: String) : AchievementListUiState()
}

class AchievementListViewModel(
    private val getPlayerAchievementsUseCase: GetPlayerAchievementsUseCase,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AchievementListUiState>(AchievementListUiState.Loading)
    val uiState: StateFlow<AchievementListUiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val id = playerRepository.getSavedSteamId()
            if (id == null) {
                _uiState.value = AchievementListUiState.Error("Steam-ID не сохранён")
            } else {
                load(id)
            }
        }
    }

    private suspend fun load(id: String) {
        _uiState.value = AchievementListUiState.Loading
        try {
            val list = getPlayerAchievementsUseCase(id)
            _uiState.value = AchievementListUiState.Success(list)
        } catch (e: Exception) {
            _uiState.value =
                AchievementListUiState.Error(e.message ?: "")
        }
    }

    fun refresh() = viewModelScope.launch(Dispatchers.IO) {
        playerRepository.getSavedSteamId()?.let { load(it) }
    }
}