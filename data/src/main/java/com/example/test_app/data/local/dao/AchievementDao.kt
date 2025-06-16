package com.example.test_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test_app.data.local.entity.AchievementEntity

@Dao
interface AchievementDao {

    @Query("SELECT * FROM achievement")
    suspend fun getAll(): List<AchievementEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<AchievementEntity>)

    @Query("SELECT * FROM achievement WHERE id = :aid")
    suspend fun getById(aid: String): AchievementEntity?

    @Query("DELETE FROM achievement")
    suspend fun clear()

    @Delete
    suspend fun delete(vararg entities: AchievementEntity)
}