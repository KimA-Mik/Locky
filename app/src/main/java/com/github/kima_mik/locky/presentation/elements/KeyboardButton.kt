package com.github.kima_mik.locky.presentation.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: Shape = CircleShape,
    content: @Composable () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = colors
    ) {
        content()
    }

//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = modifier
//            .aspectRatio(1f)
//            .background(color = color, shape = shape)
//            .clickable(onClick = onClick)
//    ) {
//        content()
//    }
}

@Composable
fun TextKeyboardButton(
    text: String,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: Shape = CircleShape,
    onClick: () -> Unit
) {
    KeyboardButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = colors
    ) {
        Text(
            text = text,
            modifier = Modifier,
            textAlign = TextAlign.Center,
            color = colors.contentColor,
            style = MaterialTheme.typography.displayLarge
        )
    }

}

@Composable
fun IconKeyboardButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: Shape = CircleShape,
    contendDescription: String? = null,
    onClick: () -> Unit
) {
    KeyboardButton(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        shape = shape
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = contendDescription,
            modifier = Modifier,
            colorFilter = ColorFilter.tint(colors.contentColor)
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