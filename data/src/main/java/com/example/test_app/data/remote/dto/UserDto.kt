package com.example.test_app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GetPlayerSummariesResponseDto(
    val response: PlayerSummaryContainerDto
)

data class PlayerSummaryContainerDto(
    val players: List<PlayerDto>
)

data class PlayerDto(
    @SerializedName("steamid") val steamId: String,
    @SerializedName("personaname") val personaname: String,
    @SerializedName("profileurl") val profileurl: String,
    @SerializedName("avatarfull") val avatarfull: String
)