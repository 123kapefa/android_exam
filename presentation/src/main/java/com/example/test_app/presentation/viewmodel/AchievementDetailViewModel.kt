package com.example.test_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_app.domain.model.PlayerAchievement
import com.example.test_app.domain.usecase.GetAchievementByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface AchievementDetailUiState {
    object Loading : AchievementDetailUiState
    data class Success(val data: PlayerAchievement) : AchievementDetailUiState
    data class Error(val msg: String) : AchievementDetailUiState
}

class AchievementDetailViewModel(
    private val id: String,
    private val getById: GetAchievementByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AchievementDetailUiState>(
        AchievementDetailUiState.Loading
    )
    val uiState: StateFlow<AchievementDetailUiState> = _uiState

    init {
        viewModelScope.launch {
            runCatching { getById(id) }
                .onSuccess { _uiState.value = AchievementDetailUiState.Success(it) }
                .onFailure { _uiState.value = AchievementDetailUiState.Error(it.message ?: "") }
        }
    }
}

//class AchievementDetailViewModel(
//    private val achievementId: String,
//    private val getAchievement: GetAchievementByIdUseCase
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow<PlayerAchievement?>(null)
//    val uiState: StateFlow<PlayerAchievement> get() = _uiState as StateFlow<PlayerAchievement>
//
//    init {
//        viewModelScope.launch {
//            _uiState.value = getAchievement(achievementId)
//        }
//    }
//}