package com.github.kima_mik.locky.presentation.screens.lockscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.kima_mik.locky.R
import com.github.kima_mik.locky.domain.code.DEFAULT_CODE_LENGTH
import com.github.kima_mik.locky.presentation.common.ComposeEvent
import com.github.kima_mik.locky.presentation.elements.keyboard.Keyboard
import com.github.kima_mik.locky.presentation.screens.lockscreen.event.LockScreenUiEvent
import com.github.kima_mik.locky.presentation.screens.lockscreen.event.LockScreenUserEvent
import com.github.kima_mik.locky.presentation.screens.lockscreen.event.OnLockScreenEvent
import com.github.kima_mik.locky.presentation.ui.theme.LockyTheme

@Composable
fun LockScreen(
    state: LockScreenState,
    snackbarHostState: SnackbarHostState,
    uiEvent: ComposeEvent<LockScreenUiEvent>,
    modifier: Modifier = Modifier,
    onEvent: OnLockScreenEvent
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LockScreenContent(
            state = state,
            snackbarHostState = snackbarHostState,
            uiEvent = uiEvent,
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
    snackbarHostState: SnackbarHostState,
    uiEvent: ComposeEvent<LockScreenUiEvent>,
    modifier: Modifier = Modifier,
    onEvent: OnLockScreenEvent
) {
    var wrongCodeTrigger by remember { mutableLongStateOf(0L) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    uiEvent.consume { lockScreenUiEvent ->
        when (lockScreenUiEvent) {
            LockScreenUiEvent.CodesNotEqual -> {
                wrongCodeTrigger = System.currentTimeMillis()
                snackbarMessage = stringResource(R.string.codes_are_not_equal_snackbar_message)
            }

            LockScreenUiEvent.ShortCode -> {
                wrongCodeTrigger = System.currentTimeMillis()
                snackbarMessage =
                    stringResource(R.string.short_code_snackbar_message, DEFAULT_CODE_LENGTH)
            }

            LockScreenUiEvent.Unlock -> TODO()
            LockScreenUiEvent.EnterApp -> TODO()
        }
    }

    LaunchedEffect(snackbarMessage) {
        if (snackbarMessage == null) return@LaunchedEffect
        snackbarMessage?.let { snackbarHostState.showSnackbar(it) }
        snackbarMessage = null
    }

    LaunchedEffect(wrongCodeTrigger) {
        if (wrongCodeTrigger == 0L) return@LaunchedEffect

    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Header(
            state.flowState,
            modifier = Modifier
                .paddingFromBaseline(bottom = 16.dp, top = 32.dp)
                .padding(horizontal = 16.dp)
        )
        InputPad(state.symbols)
        Spacer(modifier = Modifier.weight(1f))
        Keyboard(buttonShape = MaterialTheme.shapes.extraLarge) {
            onEvent(LockScreenUserEvent.KeyboardPress(it))
        }
    }
}

@Composable
fun Header(
    flowState: LockScreenFlow,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        ),
        color = MaterialTheme.colorScheme.primaryContainer,
        shadowElevation = 1.dp
    ) {
        when (flowState) {
            LockScreenFlow.FIRST_LAUNCH -> Text(
                text = stringResource(R.string.first_launch_code_header),
                modifier = modifier,
                style = MaterialTheme.typography.titleLarge
            )

            LockScreenFlow.LAUNCH -> Text(
                text = stringResource(R.string.launch_code_header),
                modifier = modifier,
                style = MaterialTheme.typography.titleLarge
            )

            LockScreenFlow.SECOND_PASSWORD_CHECK -> Text(
                text = stringResource(R.string.repeat_code_header),
                modifier = modifier,
                style = MaterialTheme.typography.titleLarge
            )

            LockScreenFlow.LOCK -> Text(
                text = stringResource(R.string.unlock_code_header),
                modifier = modifier,
                style = MaterialTheme.typography.titleLarge
            )
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
        shape = MaterialTheme.shapes.extraLarge,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
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
                uiEvent = ComposeEvent(null),
                snackbarHostState = SnackbarHostState(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                onEvent = {}
            )
        }
    }
}