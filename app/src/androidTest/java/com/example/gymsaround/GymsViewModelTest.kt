//package com.example.gymsaround
//
//import com.example.gymsaround.gyms.data.GymsRepository
//import com.example.gymsaround.gyms.data.local.GymsDAO
//import com.example.gymsaround.gyms.data.local.LocalGym
//import com.example.gymsaround.gyms.data.local.LocalGymFavoriteState
//import com.example.gymsaround.gyms.data.remote.DummyGymsList
//import com.example.gymsaround.gyms.data.remote.GymsApiService
//import com.example.gymsaround.gyms.data.remote.RemoteGym
//import com.example.gymsaround.gyms.domain.GetInitialGymsUseCase
//import com.example.gymsaround.gyms.domain.GetSortedGymsUseCase
//import com.example.gymsaround.gyms.domain.ToggleFavoriteStateUseCase
//import com.example.gymsaround.gyms.presentation.gymslist.GymScreenState
//import com.example.gymsaround.gyms.presentation.gymslist.GymsViewModel
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.StandardTestDispatcher
//import kotlinx.coroutines.test.TestScope
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.runTest
//import org.junit.Test
//
//@ExperimentalCoroutinesApi
//class GymsViewModelTest {
//    private val dispatcher = StandardTestDispatcher()
//    private val scope= TestScope(dispatcher)
//    @Test
//    fun loadingState_isSetCorrectly()= scope.runTest {
//        val viewModel = getViewModel()
//        val state= viewModel.state.value
//        assert(state == GymScreenState(
//                gyms = emptyList(),
//                isLoading = true,
//                error = null
//            ))
//    }
//
//    @Test
//    fun loadedContentState_isSetCorrectly()= scope.runTest {
//        val viewModel = getViewModel()
//        advanceUntilIdle()
//        val state= viewModel.state.value
//        assert(state == GymScreenState(
//                gyms = DummyGymsList().getDummyGymsList(),
//                isLoading = false,
//                error = null
//            ))
//    }
//    private fun getViewModel(): GymsViewModel {
//        val gymsRepository = GymsRepository(TestApiService(), TestGymsDao(),dispatcher)
//        val getSortedGymsUseCase = GetSortedGymsUseCase(gymsRepository)
//        val getInitialGymsUseCase = GetInitialGymsUseCase(gymsRepository, getSortedGymsUseCase)
//        val toggleFavoriteStateUseCase =
//            ToggleFavoriteStateUseCase(gymsRepository, getSortedGymsUseCase)
//        return GymsViewModel(getInitialGymsUseCase, toggleFavoriteStateUseCase,dispatcher)
//    }
//
//
//}
//class TestApiService : GymsApiService {
//    override suspend fun getGyms(): List<RemoteGym> {
//        return DummyGymsList().getDummyGymsList()
//    }
//
//    override suspend fun getGymsByID(id: Int): Map<String, RemoteGym> {
//        TODO("Not yet implemented")
//    }
//}
//
//class TestGymsDao : GymsDAO {
//    private var gyms = HashMap<Int, LocalGym>()
//    override suspend fun getAll(): List<LocalGym> {
//        return gyms.values.toList()
//    }
//
//    override suspend fun addAll(gyms: List<LocalGym>) {
//        gyms.forEach {
//            this.gyms[it.id] = it
//        }
//    }
//
//    override suspend fun update(localGymFavoriteState: LocalGymFavoriteState) {
//        updateGyms(localGymFavoriteState)
//    }
//
//    override suspend fun getFavoriteGyms(): List<LocalGym> {
//        return gyms.values.filter { it.isFavorite }
//    }
//
//    override suspend fun updateAll(gymsStates: List<LocalGymFavoriteState>) {
//        gymsStates.forEach {
//            updateGyms(it)
//        }
//    }
//
//    private fun updateGyms(gymsStates: LocalGymFavoriteState) {
//        val gym = this.gyms[gymsStates.id]
//        gym?.let {
//            this.gyms[gymsStates.id] = it.copy(isFavorite = gymsStates.isFavorite)
//        }
//    }
//}