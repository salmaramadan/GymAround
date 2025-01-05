package com.example.gymsaround

import retrofit2.http.GET
import retrofit2.http.Query

interface GymsApiService {
    @GET("gyms.json")
    suspend fun getGyms(): List<RemoteGym>

    @GET("gyms.json?orderBy=\"id\"")
    suspend fun getGymsByID(
        @Query("equalTo") id: Int
    ): Map<String, RemoteGym>
}
