package com.example.test_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_app.domain.usecase.GetPlayerAchievementsUseCase
import com.example.test_app.domain.usecase.GetPlayerInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel(
    private val getPlayerInfo: GetPlayerInfoUseCase,
    private val getAchievements: GetPlayerAchievementsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(steamId: String) = viewModelScope.launch(Dispatchers.IO) {
        _uiState.value = LoginUiState.Loading

        try {
            getPlayerInfo(steamId)

            delay(1500)

            getAchievements(steamId)

            _uiState.value = LoginUiState.Success
        } catch (e: Exception) {
            _uiState.value = LoginUiState.Error(
                "Превышен лимит запросов Steam API. Попробуйте чуть позже."
            )
        } catch (e: Exception) {
            _uiState.value = LoginUiState.Error(
                e.localizedMessage ?: "Не удалось подключиться к серверу"
            )
        }
    }
}