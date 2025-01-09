package com.example.gymsaround.gyms.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsaround.gyms.data.remote.GymsApiService
import com.example.gymsaround.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsDetailsViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val state = mutableStateOf<Gym?>(null)
    private var apiService: GymsApiService
    init {
        val retrofit: Retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://gymsaround-15d3c-default-rtdb.firebaseio.com/").build()

        apiService = retrofit.create(GymsApiService::class.java)
        val gymId= savedStateHandle.get<Int>("gym_id")?:0
        getGym(gymId)

    }

    private fun getGym( id: Int) {
        viewModelScope.launch {
            val gym = getGymFromRemoteDB(id)
            state.value = gym
        }
    }

    private suspend fun getGymFromRemoteDB(id: Int) = withContext(Dispatchers.IO) {
        apiService.getGymsByID(id).values.first().let { remoteGym ->
            Gym(
               id= remoteGym.id,
                gym_name = remoteGym.gym_name,
                gym_location = remoteGym.gym_location,
                is_open = remoteGym.is_open,
            )
        }
    }
}