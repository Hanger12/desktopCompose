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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.text.SimpleDateFormat
import java.util.*
import java.awt.Toolkit

@Composable
fun App() {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val screenHeight = screenSize.height

    MaterialTheme {
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight.dp - 60.dp)
                    .background(color = Color.Blue)
            )
            ToolsPanel()
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

@Composable
fun ToolsPanel() {
    val toolIconsSize = 15.dp
    val iconsSize = 30.dp
    val iconsPadding = 5.dp

    val sdf = SimpleDateFormat("dd/M/yyyy HH:mm")
    val currentDate = sdf.format(Date())

    /*val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch(Dispatchers.IO) {
        while (true) {
            withContext(Dispatchers.Main) {
                currentDate = sdf.format(Date())
            }
        }
    }*/

    val time by remember { mutableStateOf(currentDate.split(" ")[1]) }
    val date by remember { mutableStateOf(currentDate.split(" ")[0]) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = ToolsMain)
            .defaultMinSize(
                minHeight = 40.dp,
                minWidth = 1080.dp,
            ),
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
                modifier = Modifier.size(iconsSize)
            )
            Image(
                painter = painterResource("icon_search.png"),
                contentDescription = null,
                modifier = Modifier.size(iconsSize)
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
                    modifier = Modifier
                        .size(toolIconsSize)
                )
            }
            Box(
                modifier = Modifier.padding(horizontal = iconsPadding)
            ) {
                Icon(
                    painter = painterResource("icon_wifi.png"),
                    contentDescription = null,
                    modifier = Modifier
                        .size(toolIconsSize)
                )
            }
            Box(
                modifier = Modifier.padding(horizontal = iconsPadding)
            ) {
                Icon(
                    painter = painterResource("icon_sound.png"),
                    contentDescription = null,
                    modifier = Modifier
                        .size(toolIconsSize)
                )
            }
            Box(
                modifier = Modifier.padding(horizontal = iconsPadding)
            ) {
                Icon(
                    painter = painterResource("icon_battery.png"),
                    contentDescription = null,
                    modifier = Modifier.size(toolIconsSize)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = iconsPadding),
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
    }
}