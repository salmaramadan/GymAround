package com.example.gymsaround

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.gymsaround.gyms.presentation.details.GymsDetailsScreen
import com.example.gymsaround.gyms.presentation.gymslist.GymsScreen
import com.example.gymsaround.ui.theme.GymsAroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GymsAroundTheme {
                GymsAroundApp()
            }
        }
    }
}

@Composable
private fun GymsAroundApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "gyms") {
        composable(route = "gyms") {
            GymsScreen { id ->
                navController.navigate("gyms/$id")

            }
        }
        composable(
            route = "gyms/{gym_id}",
            arguments = listOf(
                navArgument("gym_id") {
                    type = NavType.IntType
                },
            ),
            deepLinks = listOf(
               navDeepLink {
//                     uriPattern = "gyms/{gym_id}"
                     uriPattern = "https://www.gymsaround.com/details/{gym_id}"
               }
            )
        ) {
//            val gymId=it.arguments?.getInt("gym_id")
            GymsDetailsScreen()
        }
    }
}

