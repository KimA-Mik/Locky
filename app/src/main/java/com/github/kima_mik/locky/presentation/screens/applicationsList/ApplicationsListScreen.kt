package com.github.kima_mik.locky.presentation.screens.applicationsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.kima_mik.locky.presentation.ui.theme.LockyTheme

@Composable
fun ApplicationsListScreen(
    state: ApplicationsListScreenState,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { innerPadding ->
        ApplicationsListScreenContent(
            state = state,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun ApplicationsListScreenContent(
    state: ApplicationsListScreenState,
    modifier: Modifier = Modifier
) {
    if (state.packages.isEmpty()) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "No packages found")
        }
    } else {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.packages) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    it.icon.imageBitmap?.let { bitmap ->
                        Image(bitmap = bitmap, contentDescription = it.name)

                    }
                    Text(text = it.name)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LockyTheme {
        ApplicationsListScreenContent(
            state = ApplicationsListScreenState(),
            modifier = Modifier.fillMaxSize()
        )
    }
}