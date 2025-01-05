package com.example.gymsaround.gyms.domain

//val listOfGyms= listOf<Gym>(
//    Gym(1,"Up Town Gym", "1234, 5th Avenue, New York"),
//    Gym(2,"Down Town Gym", "5678, 6th Avenue, New York"),
//    Gym(3,"Mid Town Gym", "91011, 7th Avenue, New York"),
//    Gym(4,"East Town Gym", "121314, 8th Avenue, New York"),
//    Gym(5,"West Town Gym", "151617, 9th Avenue, New York"),
//    Gym(6,"North Town Gym", "181920, 10th Avenue, New York"),
//    Gym(7,"North Town Gym", "181920, 10th Avenue, New York"),
//    Gym(8,"North Town Gym", "181920, 10th Avenue, New York")
//)
//data class Gym (val id : Int ,val name: String, val address: String, var isFavorite: Boolean = false)
//class GymsModel : ArrayList<GymsModelItem>()
data class Gym(
    val id: Int,
    val gym_name: String,
    val gym_location: String,
    val is_open: Boolean,
    val isFavorite: Boolean = false
)