package com.example.test_app.data.remote.api

import com.example.test_app.data.remote.dto.GetSchemaForGameResponseDto
import com.example.test_app.data.remote.dto.GetPlayerAchievementsResponseDto
import com.example.test_app.data.remote.dto.GetPlayerSummariesResponseDto
import com.example.test_app.data.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApi {

    @GET("ISteamUserStats/GetSchemaForGame/v2/")
    suspend fun getSchemaForGame(
        @Query("key") key: String,
        @Query("appid") appId: Int = BuildConfig.STEAM_APP_ID
    ): GetSchemaForGameResponseDto

    @GET("ISteamUserStats/GetPlayerAchievements/v1/")
    suspend fun getPlayerAchievements(
        @Query("key") key: String,
        @Query("steamid") steamId: String,
        @Query("appid") appId: Int = BuildConfig.STEAM_APP_ID
    ): GetPlayerAchievementsResponseDto

    @GET("ISteamUser/GetPlayerSummaries/v2/")
    suspend fun getPlayerSummaries(
        @Query("key") key: String,
        @Query("steamids") steamIds: String
    ): GetPlayerSummariesResponseDto
}