package com.example.gymsaround.gyms.data.di

import android.content.Context
import androidx.room.Room
import com.example.gymsaround.gyms.data.local.GymDataBase
import com.example.gymsaround.gyms.data.local.GymsDAO
import com.example.gymsaround.gyms.data.remote.GymsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GymsDataModule {
    @Provides
    fun provideApiServiceInstance(
        retrofit: Retrofit
    ): GymsApiService {
        return retrofit.create(GymsApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(
                "https://gymsaround-15d3c-default-rtdb.firebaseio.com/"
            )
            .build()
    }

    @Provides
    fun providesRoomDao(
        db: GymDataBase
    ): GymsDAO {
        return db.dao
    }

    @Singleton
    @Provides
    fun providesRoomDataBase(
        @ApplicationContext context: Context
    ): GymDataBase {
        return Room.databaseBuilder(
            context,
            GymDataBase::class.java,
            "gyms_db",
        ).fallbackToDestructiveMigration()
            .build()
    }
}