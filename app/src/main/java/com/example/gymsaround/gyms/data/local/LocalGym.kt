package com.example.gymsaround.gyms.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "gyms")
data class LocalGym(
    @PrimaryKey @ColumnInfo(name = "gym_id") val id: Int,
    @ColumnInfo(name = "gym_name") val gymName: String,
    @ColumnInfo(name = "gym_location") val gymLocation: String,
    @ColumnInfo(name = "is_open") val isOpen: Boolean,
    @ColumnInfo(name = "is_favorite", defaultValue = "0") val isFavorite: Boolean = false
)