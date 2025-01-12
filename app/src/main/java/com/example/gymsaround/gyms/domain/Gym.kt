
package com.example.gymsaround.gyms.domain

data class Gym(
    val id: Int,
    val gym_name: String,
    val gym_location: String,
    val is_open: Boolean,
    val isFavorite: Boolean = false
)