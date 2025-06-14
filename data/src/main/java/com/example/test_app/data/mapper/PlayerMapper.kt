package com.example.test_app.data.mapper

import com.example.test_app.data.remote.dto.PlayerDto
import com.example.test_app.domain.model.Player

fun PlayerDto.toDomain(): Player {
    return Player(
        steamId = steamId,
        personaname = personaname,
        profileurl = profileurl,
        avatarfull = avatarfull
    )
}