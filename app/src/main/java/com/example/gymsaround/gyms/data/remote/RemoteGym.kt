package com.example.gymsaround.gyms.data.remote

data class RemoteGym(
    val id: Int,
    val gymName: String,
    val gymLocation: String,
    val isOpen: Boolean
)