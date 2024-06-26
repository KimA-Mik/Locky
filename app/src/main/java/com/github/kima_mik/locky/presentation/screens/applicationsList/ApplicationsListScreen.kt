package com.github.kima_mik.locky.presentation.screens.applicationsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.kima_mik.locky.presentation.common.ImmutableImageBitmap
import com.github.kima_mik.locky.presentation.screens.applicationsList.events.AppListUserEvent
import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry
import com.github.kima_mik.locky.presentation.ui.theme.LockyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationsListScreen(
    state: ApplicationsListScreenState,
    modifier: Modifier = Modifier,
    onEvent: (AppListUserEvent) -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val sb = remember { scrollBehavior }

    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Applications",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                scrollBehavior = sb
            )
        }) { innerPadding ->
        ApplicationsListScreenContent(
            state = state,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .nestedScroll(sb.nestedScrollConnection),
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
        AppsList(
            entries = state.packages,
            modifier = modifier,
            onEvent = onEvent
        )
    }
}

@Composable
fun AppsList(
    entries: List<AppEntry>,
    modifier: Modifier = Modifier,
    onEvent: (AppListUserEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(entries,
            key = { it.packageName }) { entry ->
            ApplicationEntry(
                entry = entry,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                onSwitchClicked = { onEvent(AppListUserEvent.ApplicationToggled(entry)) }
            )
        }
    }
}

@Composable
fun ApplicationEntry(
    entry: AppEntry,
    modifier: Modifier = Modifier,
    onSwitchClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        entry.icon.imageBitmap?.let {
            Image(
                bitmap = it, contentDescription = entry.name,
                modifier = Modifier.size(60.dp)
            )
        }

        Text(
            text = entry.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Switch(checked = entry.locked, onCheckedChange = { onSwitchClicked() })
    }
}

@Preview
@Composable
private fun ApplicationEntryPreview() {
    LockyTheme {
        Surface {
            ApplicationEntry(
                entry = AppEntry(
                    name = "Preview App",
                    packageName = "",
                    icon = ImmutableImageBitmap(null),
                    locked = true
                ),
                onSwitchClicked = {}
            )
        }
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