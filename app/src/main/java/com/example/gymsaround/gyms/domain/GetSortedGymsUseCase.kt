package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsRepository
import javax.inject.Inject

class GetSortedGymsUseCase @Inject constructor(
    private val gymsRepository:GymsRepository
){
    suspend operator fun invoke() : List<Gym> {
        return gymsRepository.getGyms().sortedBy { it.gym_name }
    }
}