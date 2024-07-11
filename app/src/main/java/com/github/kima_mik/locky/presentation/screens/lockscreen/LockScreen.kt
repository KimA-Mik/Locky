package com.github.kima_mik.locky.presentation.screens.lockscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.kima_mik.locky.R
import com.github.kima_mik.locky.domain.code.DEFAULT_CODE_LENGTH
import com.github.kima_mik.locky.presentation.common.ComposeEvent
import com.github.kima_mik.locky.presentation.elements.keyboard.Keyboard
import com.github.kima_mik.locky.presentation.navigation.graphs.enterApp
import com.github.kima_mik.locky.presentation.screens.lockscreen.event.LockScreenUiEvent
import com.github.kima_mik.locky.presentation.screens.lockscreen.event.LockScreenUserEvent
import com.github.kima_mik.locky.presentation.screens.lockscreen.event.OnLockScreenEvent
import com.github.kima_mik.locky.presentation.ui.theme.LockyTheme
import kotlinx.coroutines.delay

private const val HIDDEN_PASS_SYMBOL = "⋆"

@Composable
fun LockScreen(
    state: LockScreenState,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
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
            LockScreenUiEvent.EnterApp -> navController.enterApp()
            LockScreenUiEvent.WrongCode -> {
                wrongCodeTrigger = System.currentTimeMillis()
                snackbarMessage = stringResource(R.string.wrong_code_snackbar_message)
            }
        }
    }

    LaunchedEffect(snackbarMessage) {
        if (snackbarMessage == null) return@LaunchedEffect
        snackbarMessage?.let { snackbarHostState.showSnackbar(it) }
        snackbarMessage = null
    }

    var xOffset by remember { mutableStateOf(0.dp) }
    val errorOffset by animateDpAsState(
        targetValue = xOffset,
        animationSpec = SpringSpec(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "label"
    )

    LaunchedEffect(wrongCodeTrigger) {
        if (wrongCodeTrigger == 0L) return@LaunchedEffect

        var sign = -1
        repeat(4) {
            xOffset = 15.dp * sign
            sign = -sign
            delay(32L)
        }
        xOffset = 0.dp
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LockScreenContent(
            state = state,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            digitsOffset = errorOffset,
            onEvent = onEvent
        )
    }
}

@Composable
fun LockScreenContent(
    state: LockScreenState,
    digitsOffset: Dp,
    modifier: Modifier = Modifier,
    onEvent: OnLockScreenEvent
) {
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
        InputPad(
            symbols = state.symbols,
            digitsOffset = digitsOffset,
            hidden = state.flowState == LockScreenFlow.LAUNCH || state.flowState == LockScreenFlow.LOCK
        )
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
    digitsOffset: Dp,
    hidden: Boolean,
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
                .fillMaxWidth()
                .offset(digitsOffset, 0.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            symbols.forEach {
                PadField(
                    it = it,
                    hidden = hidden
                )
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
                text = if (hidden) HIDDEN_PASS_SYMBOL else it,
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
                digitsOffset = 0.dp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                onEvent = {}
            )
        }
    }
}