package com.example.gymsaround.gyms.presentation.gymslist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.gymsaround.gyms.domain.Gym
import com.example.gymsaround.gyms.presentation.SemanticsDescription

@Composable
fun GymsScreen(
    state: GymScreenState,
    onItemClick: (Int) -> Unit,
    onFavIconClick: (id: Int, oldValue: Boolean) -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn {
            items(state.gyms) { gym ->
                GymItem(gym = gym,
                    onFavIconClick = { id, oldValue ->
                        onFavIconClick(id, oldValue)
                    },
                    onItemClick = { id ->
                        onItemClick(id)
                    }
                )
            }
        }

        if (state.isLoading) {
            Log.d("Is Loading or Not", "Yes Is Loading")
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .semantics {
                        this.contentDescription = SemanticsDescription.GYMS_LIST_LOADING
                    }
            )
        }
        state.error?.let {
            Text(text = it)
        }

    }
}

@Composable
fun GymItem(
    gym: Gym,
    onFavIconClick: (Int, Boolean) -> Unit,
    onItemClick: (Int) -> Unit
) {
    val icon = if (gym.isFavorite) Icons.Filled.Favorite
    else Icons.Filled.FavoriteBorder

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onItemClick(gym.id)
            }
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            DefaultIcon(
                Icons.Filled.Place, "Place Icon", Modifier.weight(0.15f),
            )
            GymDetails(
                gym, Modifier.weight(0.70f)
            )
            DefaultIcon(
                icon, "Favorite Gym Icon", Modifier.weight(0.15f)
            ) {
                onFavIconClick(gym.id, gym.isFavorite)
            }
        }

    }
}

@Composable
fun DefaultIcon(
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier,
    onClick: () -> Unit = {}
) {
    Image(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        colorFilter = ColorFilter.tint(
            Color.DarkGray
        )
    )
}

@Composable
fun GymDetails(
    gym: Gym,
    modifier: Modifier = Modifier,
    horizontal: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontal) {
        Text(
            text = gym.gym_name, style = MaterialTheme.typography.titleLarge, color = Color.Blue
        )

        Text(
            text = gym.gym_location,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.74f)
        )

    }
}
