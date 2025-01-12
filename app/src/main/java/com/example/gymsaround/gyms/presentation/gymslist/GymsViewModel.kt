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
    private val getInitialGymsUseCase: GetInitialGymsUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase
) : ViewModel() {

    private var _state by mutableStateOf(GymScreenState(gyms = emptyList(), isLoading = true))
    val state: State<GymScreenState>
        get() = derivedStateOf { _state }

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(isLoading = false, error = throwable.message)
    }

    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandler) {
            val receivedGyms = getInitialGymsUseCase.invoke()
            _state = _state.copy(gyms = receivedGyms, isLoading = false)
        }
    }

    fun toggleFavoriteState(gymId: Int, oldValue: Boolean) {
        viewModelScope.launch {
            val updatedGymList = toggleFavoriteStateUseCase(gymId, oldValue)
            _state = _state.copy(gyms = updatedGymList)
        }
    }
}
