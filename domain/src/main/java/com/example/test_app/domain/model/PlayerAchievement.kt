package com.example.test_app.domain.model

data class PlayerAchievement(
    val id: String,
    val title: String,
    val description: String?,
    val isHidden: Boolean,
    val iconUrl: String,
    val iconGrayUrl: String,
    val isUnlocked: Boolean = false,
    val unlockTime: Long? = null
)