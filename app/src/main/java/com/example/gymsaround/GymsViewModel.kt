package com.example.gymsaround

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    var state by mutableStateOf(emptyList<Gym>())
    private var apiService: GymsApiService
    private lateinit var gymsCall: Call<List<Gym>>

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://gymsaround-15d3c-default-rtdb.firebaseio.com/")
            .build()
        apiService = retrofit.create(GymsApiService::class.java)
        getGyms()
    }

    private fun getGyms() {
        gymsCall = apiService.getGyms()
        gymsCall.enqueue(object : Callback<List<Gym>> {
            override fun onResponse(call: Call<List<Gym>>, response: Response<List<Gym>>) {
                response.body()?.let {
                    state = it.restoreSelectedGyms()
                }
            }

            override fun onFailure(call: Call<List<Gym>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    override fun onCleared() {
        super.onCleared()
        gymsCall.cancel()
    }

    fun toggleFavoriteState(gymId: Int) {
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        gyms[itemIndex] = gyms[itemIndex].copy(isFavorite = !gyms[itemIndex].isFavorite)
        storeSelectedGym(gyms[itemIndex])
        state = gyms
    }

    private fun storeSelectedGym(gym: Gym) {
        val savedIds = stateHandle.get<List<Int>?>(FAV_IDS).orEmpty().toMutableList()
        if (gym.isFavorite) {
            savedIds.add(gym.id)
        } else {
            savedIds.remove(gym.id)
        }
        stateHandle[FAV_IDS] = savedIds
    }

    private fun List<Gym>.restoreSelectedGyms(): List<Gym> {
        val savedIds = stateHandle.get<List<Int>?>(FAV_IDS).orEmpty()
        return map { gym ->
            gym.copy(isFavorite = gym.id in savedIds)
        }
    }

    companion object {
        const val FAV_IDS = "favoriteGymsIds"
    }
}

