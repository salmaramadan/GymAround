package com.example.gymsaround

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.gymsaround.gyms.domain.GetDummyGyms.getDummyGymsList
import com.example.gymsaround.gyms.presentation.SemanticsDescription
import com.example.gymsaround.gyms.presentation.gymslist.GymScreenState
import com.example.gymsaround.gyms.presentation.gymslist.GymsScreen
import com.example.gymsaround.ui.theme.GymsAroundTheme
import org.junit.Rule
import org.junit.Test

class GymsScreenTest {
    @get :Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun loadingState_isActive() {
        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(
                    state = GymScreenState(
                        gyms = emptyList(),
                        isLoading = true,
                        error = null
                    ),
                    onItemClick = {},
                    onFavIconClick = { _: Int, _: Boolean -> }
                )
            }
        }

        // Check if the loading indicator is displayed
        testRule
            .onNodeWithContentDescription(SemanticsDescription.GYMS_LIST_LOADING)
            .assertIsDisplayed()
    }


    @Test
    fun loadingContentState_isActive() {
        val gymsList = getDummyGymsList()
        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(
                    state = GymScreenState(
                        gyms = gymsList,
                        isLoading = false,
                        error = null
                    ),
                    onItemClick = {},
                    onFavIconClick = { _: Int, _: Boolean -> }
                )
            }
        }

        // Check if the content is displayed
        testRule
            .onNodeWithText(gymsList[0].gymName)
            .assertIsDisplayed()
        testRule
            .onNodeWithContentDescription(SemanticsDescription.GYMS_LIST_LOADING)
            .assertDoesNotExist()

    }

    @Test
    fun errorState_isActive() {
        val errorText = "Failed to load gyms"
        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(
                    state = GymScreenState(
                        gyms = emptyList(),
                        isLoading = false,
                        error = errorText
                    ),
                    onItemClick = {},
                    onFavIconClick = { _: Int, _: Boolean -> }
                )
            }
        }

        // Check if the error message is displayed
        testRule
            .onNodeWithContentDescription("Error")
            .assertIsDisplayed()
    }

    @Test
    fun onItemClick_idIsPassedCorrectly() {
        val gymsList = getDummyGymsList()
        var selectedGymId = -1
        testRule.setContent {
            GymsAroundTheme {
                GymsScreen(
                    state = GymScreenState(
                        gyms = gymsList,
                        isLoading = false,
                        error = null
                    ),
                    onItemClick = { id -> selectedGymId = id },
                    onFavIconClick = { _: Int, _: Boolean -> }
                )
            }
        }

        // Click on the first gym item
        testRule
            .onNodeWithText(gymsList[0].gymName)
            .performClick()

        // Check if the correct gym id is passed
        assert(selectedGymId == gymsList[0].id)
    }
}