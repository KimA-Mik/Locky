package com.github.kima_mik.locky.presentation.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.github.kima_mik.locky.presentation.ui.theme.LockyTheme

@Composable
fun KeyboardButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = CircleShape,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .background(color = color, shape = shape)
            .clickable(onClick = onClick)
    ) {
        content()
    }
}

@Composable
fun TextKeyboardButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = CircleShape,
    onClick: () -> Unit
) {
    KeyboardButton(
        onClick = onClick,
        modifier = modifier,
        color = color,
        shape = shape
    ) {
        Text(
            text = text,
            modifier = Modifier
                .takeTwoThirds()
                .wrapContentHeight(),
            color = MaterialTheme.colorScheme.contentColorFor(color),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge
        )
    }

}

@Composable
fun IconKeyboardButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = CircleShape,
    contendDescription: String? = null,
    onClick: () -> Unit
) {
    KeyboardButton(
        onClick = onClick,
        modifier = modifier,
        color = color,
        shape = shape
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = contendDescription,
            modifier = Modifier
                .takeTwoThirds(),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.contentColorFor(color))
        )
    }
}


@Preview
@Composable
private fun KeyboardButtonPreview() {
    LockyTheme {
        KeyboardButton(
            onClick = {}
        ) {
            Text(
                text = "Hello world",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
private fun TextButtonPreview() {
    LockyTheme {
        Surface {
            TextKeyboardButton(
                text = "1",
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun IconKeyboardButtonPreview() {
    LockyTheme {
        Surface {
            IconKeyboardButton(
                imageVector = Icons.AutoMirrored.Filled.Backspace,
                onClick = {}
            )
        }
    }
}

fun Modifier.takeTwoThirds(): Modifier =
    this.layout { measurable, constraints ->
        val placeable = measurable.measure(
            constraints.copy(
                minWidth = constraints.maxWidth * 2 / 3,
                minHeight = constraints.maxHeight * 2 / 3
            )
        )

        layout(placeable.width, placeable.height) {
            placeable.placeRelative(
                0,
                0
            )
        }
    }