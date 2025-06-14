package com.example.test_app.domain.model

data class PlayerAchievement(
    val id: String,                   // "name" из schema
    val title: String,                // "displayName"
    val description: String,          // "description"
    val isHidden: Boolean,            // "hidden" == 1
    val iconUrl: String,              // "icon"
    val iconGrayUrl: String,          // "icongray"
    val isUnlocked: Boolean = false,  // из playerStats
    val unlockTime: Long? = null      // из playerStats (epoch), null если не получено
)