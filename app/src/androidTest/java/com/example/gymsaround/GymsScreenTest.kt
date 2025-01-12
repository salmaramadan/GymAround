package com.example.gymsaround

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
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
}