package presenter.compose

import ProcessBuilderCommands
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Terminal(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onDrag: (Offset) -> Unit
) {
    val commandsList by remember { mutableStateOf(mutableListOf("")) }
    var enterCommand by remember { mutableStateOf("") }
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            TerminalSystemPanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.05f)
                    .background(Color.White),
                onClose = onClose,
                onDrag = onDrag
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                commandsList.forEach { command ->
                    Text(
                        text = command,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "c:\\>",
                        color = Color.White
                    )
                    BasicTextField(
                        value = enterCommand,
                        onValueChange = { newValue ->
                            enterCommand = newValue
                        },
                        textStyle = TextStyle(
                            color = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onKeyEvent {
                                if (it.key == Key.Enter) {
                                    val result = ProcessBuilderCommands.terminalCommand(enterCommand)
                                    commandsList.add("c:\\>$enterCommand")
                                    commandsList.add(result)
                                    enterCommand = ""
                                    true
                                } else {
                                    false
                                }
                            }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TerminalSystemPanel(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onDrag: (Offset) -> Unit
) {
    Row(
        modifier = modifier.onDrag {
            onDrag.invoke(it)
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = painterResource("terminal.png"),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(0.8f)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Командная строка",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray,
                )
            )
        }
        Row {
            Image(
                painter = painterResource("icon_collapse.png"),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(0.6f)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = painterResource("icon_expand.png"),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(0.6f)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = painterResource("icon_close.png"),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .onClick { onClose.invoke() }
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}