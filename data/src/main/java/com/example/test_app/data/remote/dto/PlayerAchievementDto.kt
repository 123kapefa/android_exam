package com.example.test_app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PlayerAchievementDto(
    @SerializedName("apiname") val id: String,
    val achieved: Int,
    val unlocktime: Long
)