package com.example.gymsaround

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GymsDetailsScreen() {
    val viewModel: GymsDetailsViewModel = viewModel()
    val item = viewModel.state.value

    item?.let {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {

            DefaultIcon(
                icon = Icons.Filled.Place,
                contentDescription = "Adress Icon",
                modifier = Modifier.padding(bottom = 32.dp, top = 32.dp)
            )
            GymDetails(
                gym = item,
                modifier = Modifier.padding(32.dp),
                horizontal = Alignment.CenterHorizontally
            )
            Text(
                text = if (item.is_open) "Opened" else "Closed",
                color = if (item.is_open) Color.Green else Color.Red
            )
        }
    }
}