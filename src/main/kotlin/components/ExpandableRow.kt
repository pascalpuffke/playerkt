package components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import resources.Icons
import resources.States
import utils.withDebugBorder

@Composable
fun ExpandableRow(
    modifier: Modifier = Modifier,
    states: States,
    onClick: (() -> Unit)? = null,
    expandableContent: @Composable AnimatedVisibilityScope.() -> Unit,
    innerContent: @Composable RowScope.() -> Unit,
) {
    var expand by remember { mutableStateOf(false) }

    Row(modifier = modifier.height(48.dp).fillMaxWidth().clickable {
        if (onClick != null) onClick()
        expand = !expand
    }.withDebugBorder(states).padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        val rowScope = this

        Column(modifier = Modifier.withDebugBorder(states)) {
            Icon(imageVector = if (expand) Icons.White.expandLess else Icons.White.expandMore,
                 contentDescription = null)
        }
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth().withDebugBorder(states)) {
            innerContent.invoke(rowScope)
        }
    }

    AnimatedVisibility(visible = expand) {
        expandableContent.invoke(this)
    }
}

