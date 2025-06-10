package com.example.test_app.data.mapper

import com.example.test_app.data.local.entity.AchievementEntity
import com.example.test_app.domain.model.PlayerAchievement

// из Entity в Domain
fun AchievementEntity.toDomain(): PlayerAchievement =
    PlayerAchievement(
        id = id,
        title = title,
        description = description ?: "",
        isHidden = isHidden,
        iconUrl = iconUrl,
        iconGrayUrl = iconGrayUrl,
        isUnlocked = isUnlocked,
        unlockTime = unlockTime
    )

// из Domain в Entity
fun PlayerAchievement.toEntity(): AchievementEntity =
    AchievementEntity(
        id = id,
        title = title,
        description = description,
        isHidden = isHidden,
        iconUrl = iconUrl,
        iconGrayUrl = iconGrayUrl,
        isUnlocked = isUnlocked,
        unlockTime = unlockTime
    )