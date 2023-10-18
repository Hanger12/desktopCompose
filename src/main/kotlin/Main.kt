import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun App() {
    MaterialTheme {
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.95f)
                    .background(color = Color.Blue)
            )
            ToolsPanel(modifier = Modifier.fillMaxSize())
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

@Composable
fun ToolsPanel(modifier: Modifier = Modifier) {
    val iconsPadding = 5.dp

    Row(
        modifier = modifier
            .background(color = ToolsMain),
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource("icon_win11.png"),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(0.8f)
            )
            Image(
                painter = painterResource("icon_search.png"),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(0.8f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.padding(horizontal = iconsPadding)
            ) {
                Icon(
                    painter = painterResource("icon_up.png"),
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight(0.3f)
                )
            }
            Box(
                modifier = Modifier.padding(horizontal = iconsPadding)
            ) {
                Icon(
                    painter = painterResource("icon_wifi.png"),
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight(0.3f)
                )
            }
            Box(
                modifier = Modifier.padding(horizontal = iconsPadding)
            ) {
                Icon(
                    painter = painterResource("icon_sound.png"),
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight(0.3f)
                )
            }
            Box(
                modifier = Modifier.padding(horizontal = iconsPadding)
            ) {
                Icon(
                    painter = painterResource("icon_battery.png"),
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight(0.3f)
                )
            }
            TimeAndDate(
                startPadding = iconsPadding
            )
        }
    }
}

@Composable
fun TimeAndDate(
    startPadding: Dp
) {
    val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
    var currentDate = sdf.format(Date())

    var time by remember { mutableStateOf(currentDate.split(" ")[1]) }
    var date by remember { mutableStateOf(currentDate.split(" ")[0]) }

    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        while (true) {
            delay(1000)
            currentDate = sdf.format(Date())
            time = currentDate.split(" ")[1]
            date = currentDate.split(" ")[0]
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = startPadding),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = time,
            style = TextStyle(
                fontSize = 10.sp,
            ),
            maxLines = 1,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = date,
            style = TextStyle(
                fontSize = 10.sp
            ),
            maxLines = 1,
        )
    }
}