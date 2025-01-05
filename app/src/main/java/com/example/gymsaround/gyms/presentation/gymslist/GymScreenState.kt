package com.example.gymsaround.gyms.presentation.gymslist

import com.example.gymsaround.gyms.domain.Gym

data class GymScreenState(
    val gyms: List<Gym>,
    val isLoading: Boolean,
    val error: String? = null
)