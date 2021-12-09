package components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import resources.Icons
import resources.States
import utils.withDebugBorder

@Composable
fun ClickableRow(
    modifier: Modifier = Modifier,
    states: States,
    clicked: MutableState<Boolean>? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Row(modifier = modifier.height(48.dp).fillMaxWidth().clickable {
        if (onClick != null) onClick()
        if (clicked != null) clicked.value = !clicked.value
    }.withDebugBorder(states).padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        val rowScope = this

        Column(modifier = Modifier.withDebugBorder(states)) {
            // I hate this formatting, thank you IntelliJ
            val icon = if (clicked == null) Icons.White.settings
            else if (clicked.value) Icons.White.checkedBox
            else Icons.White.uncheckedBox
            Icon(imageVector = icon, contentDescription = null)
        }
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth().withDebugBorder(states)) {
            content.invoke(rowScope)
        }
    }
}