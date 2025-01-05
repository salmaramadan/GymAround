package com.example.gymsaround

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "gyms")
data class LocalGym(
    @ColumnInfo(name = "gym_id")
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "gym_name")
    val gym_name: String,
    @ColumnInfo(name = "gym_location")
    val gym_location: String,
    @ColumnInfo(name = "is_open")
    val is_open: Boolean,
    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    val isFavorite: Boolean = false
)