package com.example.test_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class PlayerEntity(
    @PrimaryKey val steamId: String,
    val personaname: String?,
    val profileurl: String?,
    val avatarfull: String?
)