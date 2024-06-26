package com.github.kima_mik.locky.presentation.screens.applicationsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.kima_mik.locky.presentation.common.ImmutableImageBitmap
import com.github.kima_mik.locky.presentation.screens.applicationsList.events.AppListUserEvent
import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry
import com.github.kima_mik.locky.presentation.ui.theme.LockyTheme

@Composable
fun ApplicationsListScreen(
    state: ApplicationsListScreenState,
    modifier: Modifier = Modifier,
    onEvent: (AppListUserEvent) -> Unit
) {
    Scaffold(modifier = modifier) { innerPadding ->
        ApplicationsListScreenContent(
            state = state,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onEvent = onEvent
        )
    }
}

@Composable
fun ApplicationsListScreenContent(
    state: ApplicationsListScreenState,
    modifier: Modifier = Modifier,
    onEvent: (AppListUserEvent) -> Unit
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
            items(items = state.packages,
                key = { it.packageName }) {
                ApplicationEntry(
                    entry = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onSwitch = {
                        onEvent(AppListUserEvent.ApplicationToggled(it))
                    }
                )
            }
        }
    }
}

@Composable
fun ApplicationEntry(
    entry: AppEntry,
    modifier: Modifier = Modifier,
    onSwitch: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        entry.icon.imageBitmap?.let {
            Image(
                bitmap = it, contentDescription = entry.name,
                modifier = Modifier.size(72.dp)
            )
        }

        Text(
            text = entry.name,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.weight(1f))

        Switch(checked = entry.locked, onCheckedChange = { onSwitch() })
    }
}

@Preview
@Composable
private fun ApplicationEntryPreview() {
    LockyTheme {
        ApplicationEntry(
            entry = AppEntry(
                name = "Preview App",
                packageName = "",
                icon = ImmutableImageBitmap(null),
                locked = true
            ),
            onSwitch = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LockyTheme {
        ApplicationsListScreenContent(
            state = ApplicationsListScreenState(),
            modifier = Modifier.fillMaxSize(),
            onEvent = {}
        )
    }
}