package com.example.gymsaround

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "gym_favorite")
data class LocalGymFavoriteState(
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)