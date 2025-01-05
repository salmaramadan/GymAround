package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsRepository

class ToggleFavoriteStateUseCase {
    private val gymsRepository = GymsRepository()
    private val getSortedGymsUseCase = GetSortedGymsUseCase()
    suspend operator fun invoke(gymId: Int, oldValue: Boolean): List<Gym> {
        val newState = oldValue.not()
         gymsRepository.toggleFavoriteGym(gymId, newState)
        return getSortedGymsUseCase()
    }
}