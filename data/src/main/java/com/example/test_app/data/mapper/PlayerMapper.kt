package com.example.test_app.data.mapper

import com.example.test_app.data.local.entity.PlayerEntity
import com.example.test_app.data.remote.dto.PlayerDto
import com.example.test_app.domain.model.Player

/*  DTO -> Domain  */
fun PlayerDto.toDomain() = Player(
    steamId      = steamId,
    personaname  = personaname,
    profileurl   = profileurl,
    avatarfull   = avatarfull
)

/*  Domain -> Entity  */
fun Player.toEntity() = PlayerEntity(
    steamId     = steamId,
    personaname = personaname,
    profileurl  = profileurl,
    avatarfull  = avatarfull
)

/*  Entity -> Domain  */
fun PlayerEntity.toDomain() = Player(
    steamId     = steamId,
    personaname = personaname ?: "",
    profileurl  = profileurl  ?: "",
    avatarfull  = avatarfull  ?: ""
)