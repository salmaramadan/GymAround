package com.example.gymsaround.gyms.data

import com.example.gymsaround.gyms.data.local.GymsDAO
import com.example.gymsaround.gyms.data.local.LocalGym
import com.example.gymsaround.gyms.data.local.LocalGymFavoriteState
import com.example.gymsaround.gyms.data.remote.GymsApiService
import com.example.gymsaround.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(
    private val apiService: GymsApiService,
    private val gymsDao: GymsDAO
) {
    suspend fun loadGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymsDao.getAll().isEmpty()) {
                throw Exception("No data available")
            }
        }
    }

    suspend fun getGyms(): List<Gym> {
        return withContext(Dispatchers.IO) {
            gymsDao.getAll().map {
                Gym(it.id, it.gym_name, it.gym_location, it.is_open, it.isFavorite)
            }
        }
    }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favoriteGymsList = gymsDao.getFavoriteGyms()
        gymsDao.addAll(gyms.map {
            LocalGym(
                id = it.id,
                gym_name = it.gym_name,
                gym_location = it.gym_location,
                is_open = it.is_open
            )
        })

        gymsDao.updateAll(favoriteGymsList.map {
            LocalGymFavoriteState(id = it.id, isFavorite = true)
        })
    }

    suspend fun toggleFavoriteGym(gymId: Int, state: Boolean) = withContext(Dispatchers.IO) {
        gymsDao.update(LocalGymFavoriteState(id = gymId, isFavorite = state))
        return@withContext gymsDao.getAll()
    }
}
