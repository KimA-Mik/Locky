package com.github.kima_mik.locky.presentation.elements.keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.kima_mik.locky.presentation.elements.IconKeyboardButton
import com.github.kima_mik.locky.presentation.elements.TextKeyboardButton
import com.github.kima_mik.locky.presentation.ui.theme.LockyTheme

sealed interface KeyboardEvent {
    data class Number(val code: Char) : KeyboardEvent
    data object Backspace : KeyboardEvent
    data object Enter : KeyboardEvent
}

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    buttonShape: Shape = CircleShape,
    onEvent: (KeyboardEvent) -> Unit
) {
    val space = 32.dp

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(space)
        ) {
            TextKeyboardButton(
//                modifier = Modifier.aspectRatio(1f),
                text = "1",
                shape = buttonShape
            ) {
                onEvent(KeyboardEvent.Number('1'))
            }

            TextKeyboardButton(text = "2", shape = buttonShape) {
                onEvent(KeyboardEvent.Number('2'))
            }

            TextKeyboardButton(text = "3", shape = buttonShape) {
                onEvent(KeyboardEvent.Number('3'))
            }
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(space)
        ) {
            TextKeyboardButton(text = "4", shape = buttonShape) {
                onEvent(KeyboardEvent.Number('4'))
            }

            TextKeyboardButton(text = "5", shape = buttonShape) {
                onEvent(KeyboardEvent.Number('5'))
            }

            TextKeyboardButton(text = "6", shape = buttonShape) {
                onEvent(KeyboardEvent.Number('6'))
            }
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(space)
        ) {
            TextKeyboardButton(text = "7", shape = buttonShape) {
                onEvent(KeyboardEvent.Number('7'))
            }

            TextKeyboardButton(text = "8", shape = buttonShape) {
                onEvent(KeyboardEvent.Number('8'))
            }

            TextKeyboardButton(text = "9", shape = buttonShape) {
                onEvent(KeyboardEvent.Number('9'))
            }
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(space),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconKeyboardButton(
                imageVector = Icons.AutoMirrored.Filled.Backspace,
                shape = buttonShape,
                modifier = Modifier
//                    .height(IntrinsicSize.Min)
//                    .width(IntrinsicSize.Min)
//                    .aspectRatio(1f)
//                    .weight(1f)
            ) {
                onEvent(KeyboardEvent.Backspace)
            }

            TextKeyboardButton(
                text = "0", shape = buttonShape
            ) {
                onEvent(KeyboardEvent.Number('0'))
            }

            IconKeyboardButton(
                imageVector = Icons.Default.Check,
                shape = buttonShape,
//                Modifier
//                    .height(IntrinsicSize.Min)
//                    .width(IntrinsicSize.Min)
//                    .aspectRatio(1f)
//                    .weight(1f)
            ) {
                onEvent(KeyboardEvent.Enter)
            }
        }
    }
}


@Preview
@Composable
private fun KeyboardPreview() {
    LockyTheme {
        Surface {
            Keyboard(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 64.dp)
            ) {

            }
        }
    }
}