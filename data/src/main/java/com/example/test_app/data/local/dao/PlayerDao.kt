package com.example.test_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test_app.data.local.entity.AchievementEntity
import com.example.test_app.data.local.entity.PlayerEntity

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player LIMIT 1")
    suspend fun getCurrent(): PlayerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: PlayerEntity)

    @Query("DELETE FROM player")
    suspend fun clear()

    @Delete
    suspend fun delete(vararg entities: PlayerEntity)
}