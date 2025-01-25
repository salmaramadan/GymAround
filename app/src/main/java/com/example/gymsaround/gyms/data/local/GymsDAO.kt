package com.example.gymsaround.gyms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GymsDAO {
    @Query("SELECT * FROM gyms")
    suspend fun getAll(): List<LocalGym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gyms: List<LocalGym>)

    @Update(entity = LocalGym::class)
    suspend fun update(localGymFavoriteState: LocalGymFavoriteState)

    @Query("SELECT * FROM gyms WHERE is_favorite = 1")
    suspend fun getFavoriteGyms(): List<LocalGym>

    @Update(entity = LocalGym::class)
    suspend fun updateAll(gymsStates: List<LocalGymFavoriteState>)
}