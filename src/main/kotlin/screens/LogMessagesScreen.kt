package screens

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.MessageType
import resources.States
import utils.fromUnixTime
import utils.withDebugBorder
import java.time.format.DateTimeFormatter

@Composable
fun LogMessagesScreen(
    states: States,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val lazyListState = rememberLazyListState()

        LazyColumn(state = lazyListState,
                   reverseLayout = true,
                   modifier = Modifier.padding(5.dp).withDebugBorder(states)) {
            itemsIndexed(states.messages) { index, message ->
                val formattedTime = DateTimeFormatter.ISO_INSTANT.fromUnixTime(message.timestamp)
                val timeDiff =
                    message.timestamp - (states.messages.getOrNull(index - 1)?.timestamp ?: message.timestamp)
                val timestamp = "$formattedTime (+ $timeDiff ms)"

                Spacer(Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(start = 10.dp, end = 5.dp)
                    .withDebugBorder(states)) {
                    Text(text = message.message, color = when (message.type) {
                        MessageType.Error -> states.theme.value.error
                        MessageType.Warning -> states.theme.value.secondary
                        MessageType.Info -> states.theme.value.onSurface
                    })
                }
                Row(modifier = Modifier.fillMaxWidth().padding(5.dp).withDebugBorder(states)) {
                    Text(text = timestamp, color = states.theme.value.textContrast)
                }
            }
        }

        VerticalScrollbar(modifier = Modifier.align(Alignment.CenterEnd).withDebugBorder(states),
                          reverseLayout = true,
                          adapter = rememberScrollbarAdapter(lazyListState))
    }
}