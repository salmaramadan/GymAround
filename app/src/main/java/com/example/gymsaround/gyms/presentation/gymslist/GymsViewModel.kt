package com.example.gymsaround.gyms.presentation.gymslist

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsaround.gyms.domain.GetInitialGymsUseCase
import com.example.gymsaround.gyms.domain.ToggleFavoriteStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class GymsViewModel @Inject constructor(
    private val grtInitialGymsUseCase: GetInitialGymsUseCase,
    private val toggleFavoriteStateUseCase : ToggleFavoriteStateUseCase
) : ViewModel() {

    private var _state by mutableStateOf(
        GymScreenState
            (gyms = emptyList(), isLoading = true)
    )

    val state: State<GymScreenState>
        get() = derivedStateOf { _state }
//    private lateinit var gymsCall: Call<List<Gym>>
    //    val job = Job()
//    val scope = CoroutineScope(Dispatchers.IO + job)

//    private val repo = GymsRepository()

    private val errorHandler = CoroutineExceptionHandler() { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(isLoading = false, error = throwable.message)
    }


    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandler) {
            try {
//                val gyms = getAllGyms()
                val receivedGyms = grtInitialGymsUseCase.invoke()
                _state = _state.copy(gyms = receivedGyms, isLoading = false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


//        gymsCall = apiService.getGyms()
//        gymsCall.enqueue(object : Callback<List<Gym>> {
//            override fun onResponse(call: Call<List<Gym>>, response: Response<List<Gym>>) {
//                response.body()?.let {
//                    state = it.restoreSelectedGyms()
//                }
//            }
//
//            override fun onFailure(call: Call<List<Gym>>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })
    }


//    override fun onCleared() {

//        super.onCleared()
////        gymsCall.cancel()
//        job.cancel()
//    }

    fun toggleFavoriteState(gymId: Int,oldValue:Boolean) {
       // val gyms = _state.gyms.toMutableList()
        //val itemIndex = gyms.indexOfFirst { it.id == gymId }
//        gyms[itemIndex] = gyms[itemIndex].copy(isFavorite = !gyms[itemIndex].isFavorite)
//        storeSelectedGym(gyms[itemIndex])
//        state = gyms
        viewModelScope.launch {

//            val updatedGymList = repo.toggleFavoriteGym(gymId, !gyms[itemIndex].isFavorite)
            val updatedGymList = toggleFavoriteStateUseCase(gymId, oldValue)
            _state = _state.copy(
                gyms = updatedGymList
            )

        }
    }


//    private fun storeSelectedGym(gym: Gym) {
//        val savedIds = stateHandle.get<List<Int>?>(FAV_IDS).orEmpty().toMutableList()
//        if (gym.isFavorite) {
//            savedIds.add(gym.id)
//        } else {
//            savedIds.remove(gym.id)
//        }
//        stateHandle[FAV_IDS] = savedIds
//    }
//
//    private fun List<Gym>.restoreSelectedGyms(): List<Gym> {
//        stateHandle.get<List<Int>?>(FAV_IDS)?.let { savedIds ->
//            val getMap = this.associateBy { it.id }.toMutableMap()
//            savedIds.forEach { gymId ->
//                val gym = getMap[gymId] ?: return@forEach
//                getMap[gymId] = gym.copy(isFavorite = true)
//            }
//            return getMap.values.toList()
//        }
//        return this
//    }

//    companion object {
//        const val FAV_IDS = "favoriteGymsIds"
//    }
}

