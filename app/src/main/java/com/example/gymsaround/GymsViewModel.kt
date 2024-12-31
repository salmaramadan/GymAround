package com.example.gymsaround

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GymsViewModel() : ViewModel() {

    var state by mutableStateOf(getGyms())

    private fun getGyms() = listOfGyms

    fun toggleFavoriteState(gymId: Int) {
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        gyms[itemIndex] = gyms[itemIndex].copy(isFavorite = !gyms[itemIndex].isFavorite)
        state = gyms
    }
}