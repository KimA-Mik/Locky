package com.github.kima_mik.locky.presentation.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.github.kima_mik.locky.presentation.ui.theme.LockyTheme

@Composable
fun SimpleAlertDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    title: String,
    text: String,
    icon: ImageVector,
    confirmText: String = "Confirm",
    dismissText: String = "Dismiss"
) {
    AlertDialog(
        icon = { Icon(imageVector = icon, contentDescription = null) },
        title = { Text(text = title) },
        text = { Text(text = text) },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = dismissText)
            }
        })
}


@Preview(name = "Normal theme")
@Preview(
    name = "Night theme",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DialogPreview() {
    LockyTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SimpleAlertDialog(
                onConfirm = {  },
                onDismiss = {  },
                title = "Dialog",
                text = "Text",
                icon = Icons.Default.Build
            )
        }
    }
}
