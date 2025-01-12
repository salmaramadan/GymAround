package com.example.gymsaround.gyms.data.remote

data class RemoteGym(
    val id: Int,
    val gym_name: String,
    val gym_location: String,
    val is_open: Boolean
)