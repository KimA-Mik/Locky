package com.github.kima_mik.locky.presentation.screens.applicationsList

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.kima_mik.locky.R
import com.github.kima_mik.locky.presentation.common.ComposeEvent
import com.github.kima_mik.locky.presentation.common.ImmutableImageBitmap
import com.github.kima_mik.locky.presentation.elements.SimpleAlertDialog
import com.github.kima_mik.locky.presentation.screens.applicationsList.events.AppListUiEvent
import com.github.kima_mik.locky.presentation.screens.applicationsList.events.AppListUserEvent
import com.github.kima_mik.locky.presentation.screens.applicationsList.model.AppEntry
import com.github.kima_mik.locky.presentation.ui.theme.LockyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationsListScreen(
    state: ApplicationsListScreenState,
    modifier: Modifier = Modifier,
    uiEvents: State<ComposeEvent<AppListUiEvent>>,
    onEvent: (AppListUserEvent) -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val sb = remember { scrollBehavior }

    val uiEvent by uiEvents
    uiEvent.consume {
        when (it) {
            AppListUiEvent.RequirePackageUsageStats ->
                LocalContext.current.startActivity(
                    Intent(
                        Settings.ACTION_USAGE_ACCESS_SETTINGS,
                        Uri.parse("package:${LocalContext.current.packageName}")
                    )
                )

            AppListUiEvent.RequireMangeOverlay ->
                LocalContext.current.startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:${LocalContext.current.packageName}")
                    )
                )
        }
    }

    when {
        state.showGrantPackageUsageStatsDialog ->
            SimpleAlertDialog(
                onConfirm = { onEvent(AppListUserEvent.ConfirmGrantPackageUsageStatsDialog) },
                onDismiss = { onEvent(AppListUserEvent.DismissGrantPackageUsageStatsDialog) },
                title = stringResource(R.string.permission_required_dialog_title),
                text = stringResource(R.string.grant_package_usage_stats_dialog_text),
                icon = Icons.Default.Warning,
                confirmText = stringResource(R.string.dialog_proceed_button_text),
                dismissText = stringResource(R.string.dialog_dismiss_button_text)
            )

        state.showRequireMangeOverlayDialog -> {
            SimpleAlertDialog(
                onConfirm = { onEvent(AppListUserEvent.ConfirmGrantManageOverlayDialog) },
                onDismiss = { onEvent(AppListUserEvent.DismissGrantManageOverlayDialog) },
                title = stringResource(R.string.permission_required_dialog_title),
                text = stringResource(R.string.grant_manage_overlay_dialog_text),
                icon = Icons.Default.Warning,
                confirmText = stringResource(R.string.dialog_proceed_button_text),
                dismissText = stringResource(R.string.dialog_dismiss_button_text)
            )
        }
    }

    Scaffold(modifier = modifier,
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "Applications",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    },
                    scrollBehavior = sb
                )
                if (state.packages is ApplicationsListScreenState.Packages.Loading) {
                    LinearProgressIndicator(
                        progress = { state.packages.progress },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
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
    if (state.packages is ApplicationsListScreenState.Packages.Entries) {
        if (state.packages.data.isEmpty()) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = "No packages found")
            }
        } else {
            AppsList(
                entries = state.packages.data,
                modifier = modifier,
                onEvent = onEvent
            )
        }
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