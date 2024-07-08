package com.github.kima_mik.locky.presentation.screens.lockscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.kima_mik.locky.presentation.elements.keyboard.Keyboard
import com.github.kima_mik.locky.presentation.screens.lockscreen.event.LockScreenUserEvent
import com.github.kima_mik.locky.presentation.screens.lockscreen.event.OnLockScreenEvent
import com.github.kima_mik.locky.presentation.ui.theme.LockyTheme

@Composable
fun LockScreen(
    state: LockScreenState,
    modifier: Modifier = Modifier,
    onEvent: OnLockScreenEvent
) {
    Scaffold { paddingValues ->
        LockScreenContent(
            state = state,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            onEvent = onEvent
        )
    }
}

@Composable
fun LockScreenContent(
    state: LockScreenState,
    modifier: Modifier = Modifier,
    onEvent: OnLockScreenEvent
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        InputPad(state.symbols)
        Keyboard(buttonShape = MaterialTheme.shapes.extraLarge) {
            onEvent(LockScreenUserEvent.KeyboardPress(it))
        }
    }
}

@Composable
fun InputPad(
    symbols: List<String?>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            symbols.forEach {
                PadField(it = it)
            }
        }
    }
}

@Composable
fun PadField(
    it: String?,
    modifier: Modifier = Modifier,
    hidden: Boolean = false
) {
    AnimatedContent(
        targetState = it,
        modifier = modifier,
        label = it ?: "Empty"
    ) {
        if (it == null) {
            Text(
                text = " ",
                style = MaterialTheme.typography.displayMedium
            )
        } else {
            Text(
                text = it,
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}

@Preview
@Composable
private fun LockScreenContentPreview() {
    LockyTheme {
        Surface {
            LockScreenContent(
                state = LockScreenState(
                    symbols = List<String?>(4) { it.toString() }
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                onEvent = {}
            )
        }
    }
}