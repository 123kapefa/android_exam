package com.example.test_app.data.mapper

import com.example.test_app.data.remote.dto.AchievementSchemaDto
import com.example.test_app.data.remote.dto.PlayerAchievementDto
import com.example.test_app.domain.model.PlayerAchievement

fun AchievementSchemaDto.toDomain(): PlayerAchievement {
    return PlayerAchievement(
        id = id,
        title = title,
        description = description,
        isHidden = hidden == 1,
        iconUrl = icon,
        iconGrayUrl = iconGrayUrl
    )
}

fun PlayerAchievement.updateWithPlayerData(player: PlayerAchievementDto): PlayerAchievement {
    return this.copy(
        isUnlocked = player.achieved == 1,
        unlockTime = if (player.achieved == 1) player.unlocktime else null
    )
}