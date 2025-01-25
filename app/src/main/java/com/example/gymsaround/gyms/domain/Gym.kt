
package com.example.gymsaround.gyms.domain

data class Gym(
    val id: Int,
    val gymName: String,
    val gymLocation: String,
    val isOpen: Boolean,
    val isFavorite: Boolean = false
)