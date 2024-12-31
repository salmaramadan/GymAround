package com.example.gymsaround

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class GymsViewModel(private val stateHandel: SavedStateHandle) : ViewModel() {

    var state by mutableStateOf(restoreSelectedGym())

    private fun getGyms() = listOfGyms

    fun toggleFavoriteState(gymId: Int) {
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        gyms[itemIndex] = gyms[itemIndex].copy(isFavorite = !gyms[itemIndex].isFavorite)
        storeSelectedGym(gyms[itemIndex])
        state = gyms
    }
    private fun storeSelectedGym(gym: Gym){
        val savedHandelList = stateHandel.get<List<Int>?>(FAV_IDS).orEmpty().toMutableList()
        if (gym.isFavorite) {
            savedHandelList.add(gym.id)
        } else {
            savedHandelList.remove(gym.id)
        }
        stateHandel[FAV_IDS] = savedHandelList

    }


    private fun restoreSelectedGym(): List<Gym> {
        val savedIds = stateHandel.get<List<Int>>(FAV_IDS).orEmpty()
        return listOfGyms.map { gym ->
            gym.copy(isFavorite = savedIds.contains(gym.id))
        }
    }

    //    private fun restoreSelectedGym():List<Gym>{
//        val gyms = state.toMutableList()
//        stateHandel.get<List<Int>?>(FAV_IDS).let { savedIds ->
//            savedIds?.forEach { id ->
//                gyms.find { it.id == id }?.isFavorite = true
//            }
//        }
//        return gyms
//    }
    companion object{
        const val FAV_IDS = "favoriteGymsIds"
    }
}