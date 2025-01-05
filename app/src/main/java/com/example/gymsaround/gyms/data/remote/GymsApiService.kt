package com.example.gymsaround.gyms.data.remote

import com.example.gymsaround.gyms.data.remote.RemoteGym
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
