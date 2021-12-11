package screens

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import components.ClickableRow
import components.ExpandableRow
import components.SingleLineText
import resources.Icons
import resources.States
import resources.Theme
import utils.rescanLibrary
import utils.toggleBorders
import utils.withDebugBorder

@Composable
fun CategoryTitle(text: String, states: States) {
    Row(modifier = Modifier.height(24.dp).withDebugBorder(states), verticalAlignment = Alignment.CenterVertically) {
        SingleLineText(text)
    }
}

@Composable
fun SettingsScreen(
    states: States,
) {
    val bordersEnabled = remember { mutableStateOf(states.borderStroke.value.width == 1.dp) }

    Surface(modifier = Modifier.background(states.theme.value.surface).padding(16.dp, 8.dp)) {
        Box {
            val lazyListState = rememberLazyListState()

            LazyColumn(state = lazyListState, modifier = Modifier.withDebugBorder(states)) {
                // No idea how to do this properly.
                // One item per element? Per section?
                item {
                    CategoryTitle("Library management", states)
                    ExpandableRow(states = states, innerContent = {
                        SingleLineText("Search paths")
                    }, expandableContent = {
                        states.paths.forEachIndexed { index, s ->
                            Column {
                                TextField(value = s, modifier = Modifier.fillMaxWidth().padding(5.dp), onValueChange = {
                                    states.paths[index] = it
                                })
                            }
                        }
                    })
                    ClickableRow(states = states, onClick = {
                        rescanLibrary(states)
                    }) {
                        SingleLineText("Refresh library")
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    CategoryTitle("Theme", states)
                    ExpandableRow(states = states, innerContent = {
                        SingleLineText(buildAnnotatedString {
                            append("Select theme ")

                            withStyle(SpanStyle(color = states.theme.value.textContrast)) {
                                append("(${states.theme.value})")
                            }
                        })
                    }, expandableContent = {
                        Column {
                            for (theme in Theme.values()) {
                                Row(modifier = Modifier.height(48.dp).fillMaxWidth().padding(5.dp)
                                    .withDebugBorder(states).clickable {
                                        states.theme.value = theme
                                    }, verticalAlignment = Alignment.CenterVertically) {
                                    val color = if (states.theme.value == theme) {
                                        states.theme.value.primaryVariant
                                    } else {
                                        states.theme.value.onSurface
                                    }

                                    Column(modifier = Modifier.padding(start = 16.dp).withDebugBorder(states)) {
                                        Icon(imageVector = if (theme.colors.isLight) Icons.Black.lightMode else Icons.Black.darkMode,
                                             tint = color,
                                             contentDescription = null)
                                    }
                                    Spacer(Modifier.width(8.dp))
                                    Column(modifier = Modifier.fillMaxWidth().withDebugBorder(states)) {
                                        SingleLineText(text = theme.toString(), color = color)
                                    }
                                }
                            }
                        }
                    })

                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    CategoryTitle("Debug", states)
                    ClickableRow(states = states, clicked = bordersEnabled, onClick = {
                        val result = toggleBorders(states)
                        println("Toggled borders: $result")
                        bordersEnabled.value = result
                    }) {
                        SingleLineText("Draw UI boundaries")
                    }
                }
            }

            VerticalScrollbar(modifier = Modifier.align(Alignment.CenterEnd).withDebugBorder(states),
                              adapter = rememberScrollbarAdapter(lazyListState))
        }
    }
}