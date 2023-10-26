package presenter.compose

import DeviceManagerViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.DeviceGroup
import models.DeviceType

@Composable
fun DeviceManager(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Box {
                DeviceManagerToolsPanel(
                    modifier = Modifier
                    .fillMaxHeight(0.05f)
                )
            }
            Box {
                DeviceManagerIconsPanel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.05f)
                )
            }
            Box {
                DeviceManagerBody(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun DeviceManagerToolsPanel(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start
    ) {
        ToolPanelButton(
            text = "Файл",
            onClick = {}
        )
        ToolPanelButton(
            text = "Действие",
            onClick = {}
        )
        ToolPanelButton(
            text = "Вид",
            onClick = {}
        )
        ToolPanelButton(
            text = "Справка",
            onClick = {}
        )
    }
}

@Composable
fun DeviceManagerIconsPanel(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Image(
            painter = painterResource("icon_file.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(0.8f)
        )
        Image(
            painter = painterResource("icon_file.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(0.8f)
        )
        Image(
            painter = painterResource("icon_file.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(0.8f)
        )
        Image(
            painter = painterResource("icon_file.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(0.8f)
        )
        Image(
            painter = painterResource("icon_file.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(0.8f)
        )
        Image(
            painter = painterResource("icon_file.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(0.8f)
        )
        Image(
            painter = painterResource("icon_file.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(0.8f)
        )
    }
}

@Composable
fun DeviceManagerBody(modifier: Modifier = Modifier) {
    val list by DeviceManagerViewModel.devices.collectAsState()

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        list.forEach { devicesGroup ->
            DevicesView(
                devicesGroup = devicesGroup,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DevicesView(
    devicesGroup: DeviceGroup?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        var visible by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onClick { visible = !visible }
        ) {
            Image(
                painter = painterResource("icon_up.png"),
                contentDescription = null,
                modifier = Modifier.size(10.dp)
            )
            Image(
                painter = painterResource(
                    resourcePath = when(devicesGroup?.type) {
                        DeviceType.Audio -> "icon_file.png"
                        else -> "icon_file.png"
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = devicesGroup?.name ?: "",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Black,
                )
            )
        }
        AnimatedVisibility(visible = visible) {
            Column {
                devicesGroup?.devices?.forEach { device ->
                    Row(modifier = Modifier.fillMaxWidth().padding(start = 30.dp)) {
                        Image(
                            painter = painterResource("icon_file.png"),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = device.name,
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.Black,
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ToolPanelButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Black,
            )
        )
    }
}