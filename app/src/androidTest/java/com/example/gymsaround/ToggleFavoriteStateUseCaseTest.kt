//package com.example.gymsaround
//
//import com.example.gymsaround.gyms.data.GymsRepository
//import com.example.gymsaround.gyms.domain.GetDummyGyms
//import com.example.gymsaround.gyms.domain.GetSortedGymsUseCase
//import com.example.gymsaround.gyms.domain.ToggleFavoriteStateUseCase
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.StandardTestDispatcher
//import kotlinx.coroutines.test.TestScope
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.runTest
//import org.junit.Test
//
//@ExperimentalCoroutinesApi
//class ToggleFavoriteStateUseCaseTest {
//    private val dispatcher = StandardTestDispatcher()
//    private val scope = TestScope(dispatcher)
//
//    @Test
//    fun toggleFavoriteState_UpdateFavoriteProperty() = scope.runTest {
//        val gymsRepo : GymsRepository = GymsRepository(TestApiService(), TestGymsDao(), dispatcher)
//        val getSortedGymsUseCase:GetSortedGymsUseCase = GetSortedGymsUseCase(gymsRepo)
//        val useCaseUnderTest:ToggleFavoriteStateUseCase = ToggleFavoriteStateUseCase(gymsRepo, getSortedGymsUseCase)
//
//        gymsRepo.loadGyms()
//        advanceUntilIdle()
//
//        val gyms=GetDummyGyms.getDummyGymsList()
//        val getUnderTest =gyms[0]
//        val isFav = getUnderTest.isFavorite
//
//        val updatedGymsList = useCaseUnderTest.invoke(getUnderTest.id, isFav)
//        advanceUntilIdle()
//
//        assert(updatedGymsList[0].isFavorite != isFav)
//    }
//}