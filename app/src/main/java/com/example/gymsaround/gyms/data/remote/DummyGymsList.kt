package com.example.gymsaround.gyms.data.remote

object DummyGymsList {
    fun getDummyGymsList() = arrayListOf(
        RemoteGym(1, "Gym 1", "Location 1", true),
        RemoteGym(2, "Gym 2", "Location ", true),
        RemoteGym(3, "Gym 3", "Location 3", true),
        RemoteGym(4, "Gym 4", "Location 4", true),
        RemoteGym(5, "Gym 5", "Location 5", true),
        RemoteGym(6, "Gym 6", "Location 6", true),
        RemoteGym(7, "Gym 7", "Location 7", true)
    )
}