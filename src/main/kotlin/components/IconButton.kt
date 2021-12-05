package components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun IconButton(
    icon: ImageVector,
    debugBorderStroke: MutableState<BorderStroke>,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: Shape = CircleShape,
    iconPadding: Dp = 2.dp,
    onClick: () -> Unit = {},
) {
    Button(
        shape = shape,
        modifier = modifier.aspectRatio(1f).border(debugBorderStroke.value),
        contentPadding = PaddingValues(iconPadding),
        colors = colors,
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon, contentDescription = null, modifier = Modifier.fillMaxSize()
        )
    }
}

