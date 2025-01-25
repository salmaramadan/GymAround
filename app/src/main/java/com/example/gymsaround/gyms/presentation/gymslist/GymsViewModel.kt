package com.example.gymsaround.gyms.presentation.gymslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsaround.gyms.data.di.MainDispatcher
import com.example.gymsaround.gyms.domain.GetInitialGymsUseCase
import com.example.gymsaround.gyms.domain.ToggleFavoriteStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymsViewModel @Inject constructor(
    private val getInitialGymsUseCase: GetInitialGymsUseCase,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _state by mutableStateOf(GymScreenState(gyms = emptyList(), isLoading = true))
    val state: GymScreenState
        get() = _state

    init {
        fetchGyms()
    }

    private fun fetchGyms() {
        viewModelScope.launch(dispatcher + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            _state = _state.copy(isLoading = false, error = throwable.message ?: "An error occurred")
        }) {
            _state = _state.copy(isLoading = true)
            val gyms = getInitialGymsUseCase()
            _state = _state.copy(gyms = gyms, isLoading = false)
        }
    }

    fun toggleFavoriteState(gymId: Int, oldValue: Boolean) {
        viewModelScope.launch(dispatcher) {
            val updatedGyms = toggleFavoriteStateUseCase(gymId, oldValue)
            _state = _state.copy(gyms = updatedGyms)
        }
    }
}
