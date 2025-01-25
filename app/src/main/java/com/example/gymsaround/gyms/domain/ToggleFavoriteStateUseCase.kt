package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsRepository
import javax.inject.Inject

class ToggleFavoriteStateUseCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getSortedGymsUseCase: GetSortedGymsUseCase
) {
    suspend operator fun invoke(gymId: Int, oldValue: Boolean): List<Gym> {
        val newState = !oldValue
        gymsRepository.toggleFavoriteGym(gymId, newState)
        return getSortedGymsUseCase()
    }
}