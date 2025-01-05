package com.example.gymsaround.gyms.domain

import com.example.gymsaround.GymsRepository

class GetSortedGymsUseCase {
    private val gymsRepository= GymsRepository()
    suspend operator fun invoke() : List<Gym> {
        return gymsRepository.getGyms().sortedBy { it.gym_name }
    }
}