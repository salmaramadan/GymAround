package com.example.gymsaround.gyms.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalGym::class], version = 3, exportSchema = false)
abstract class GymDataBase : RoomDatabase() {
    abstract val dao: GymsDAO

    companion object {
        @Volatile
        private var databaseInstance: GymDataBase? = null

        fun getDaoInstance(context: Context): GymsDAO {
            if (databaseInstance == null) {
                synchronized(this) {
                    if (databaseInstance == null) {
                        databaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            GymDataBase::class.java,
                            "gyms_db"
                        ).fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return databaseInstance!!.dao
        }
    }
}
