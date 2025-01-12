package com.example.gymsaround.gyms.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalGym::class], version = 3, exportSchema = false)
abstract class GymDataBase : RoomDatabase() {
    abstract val dao: GymsDAO
}


