package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsRepository

class GetInitialGymsUseCase {
    private val gymsRepository= GymsRepository()
    private val getSortedGymsUseCase = GetSortedGymsUseCase()
    suspend operator fun invoke() : List<Gym> {
         gymsRepository.loadGyms()
        return getSortedGymsUseCase()
    }

}