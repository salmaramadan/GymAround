package com.example.gymsaround.gyms.data

import com.example.gymsaround.gyms.data.di.IODispatcher
import com.example.gymsaround.gyms.data.local.GymsDAO
import com.example.gymsaround.gyms.data.local.LocalGym
import com.example.gymsaround.gyms.data.local.LocalGymFavoriteState
import com.example.gymsaround.gyms.data.remote.GymsApiService
import com.example.gymsaround.gyms.domain.Gym
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(
    private val apiService: GymsApiService,
    private val gymsDao: GymsDAO,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun loadGyms() = withContext(dispatcher) {
        try {
            val gyms = apiService.getGyms()
            if (gyms.isNotEmpty()) {
                gymsDao.addAll(gyms.map {
                    LocalGym(it.id, it.gymName, it.gymLocation, it.isOpen)
                })
            }
        } catch (e: Exception) {
            println("Error loading gyms: ${e.message}")
            if (gymsDao.getAll().isEmpty()) throw Exception("No data available")
        }
    }

    suspend fun getGyms(): List<Gym> = withContext(dispatcher) {
        val gyms = gymsDao.getAll()
        gyms.map {
            Gym(it.id, it.gymName, it.gymLocation, it.isOpen, it.isFavorite)
        }
    }

    suspend fun toggleFavoriteGym(gymId: Int, state: Boolean) = withContext(dispatcher) {
        gymsDao.update(LocalGymFavoriteState(gymId, state))
    }
}
