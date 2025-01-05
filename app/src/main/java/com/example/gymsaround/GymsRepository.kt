package com.example.gymsaround

import com.example.gymsaround.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsRepository {

    private val apiService: GymsApiService = Retrofit.Builder().addConverterFactory(
        GsonConverterFactory.create()
    ).baseUrl("https://gymsaround-15d3c-default-rtdb.firebaseio.com/").build()
        .create(GymsApiService::class.java)


    private var gymsDao = GymDataBase.getDaoInstance(
        GymsApplication.getApplicationContext()
    )


    suspend fun loadGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymsDao.getAll().isEmpty()) {
                throw Exception("No data available")
            }
        }
//        gymsDao.getAll()
    }

    suspend fun getGyms(): List<Gym> {
        return withContext(Dispatchers.IO) {
            return@withContext gymsDao.getAll()
                .map { Gym(it.id, it.gym_name, it.gym_location, it.is_open, it.isFavorite) }
        }
    }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()

        val favoriteGymsList = gymsDao.getFavoriteGyms()
        gymsDao.addAll(gyms.map {
            LocalGym(
                it.id,
                it.gym_name,
                it.gym_location,
                it.is_open,
            )
        })

        gymsDao.updateAll(favoriteGymsList.map {
            LocalGymFavoriteState(
                id = it.id, isFavorite = true
            )
        })
    }

    suspend fun toggleFavoriteGym(gymId: Int, state: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.update(
                LocalGymFavoriteState(
                    id = gymId, isFavorite = state
                )
            )
            return@withContext gymsDao.getAll()
        }
}