package presenter

import DeviceManagerViewModel
import UserViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import presenter.compose.*

@Composable
fun App(
    exitCallback: () -> Unit,
) {
    MaterialTheme {
        var startMenuVisible by remember { mutableStateOf(false) }
        var login by remember { mutableStateOf(false) }

        var deviceManagerVisible by remember { mutableStateOf(false) }
        var deviceManagerOffset by remember { mutableStateOf(Offset(0f, 0f)) }

        if (login) {
            Box {
                Column(
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.95f)
                    ) {
                        DesktopBox(
                            modifier = Modifier
                                .fillMaxSize()
                                .paint(
                                    painter = painterResource("desktop.png"),
                                    contentScale = ContentScale.FillBounds,
                                ),
                        )
                    }
                    ToolsPanel(
                        modifier = Modifier.fillMaxSize(),
                        startMenuVisibleCallback = { startMenuVisible = !startMenuVisible },
                        deviceManagerClick = { deviceManagerVisible = !deviceManagerVisible }
                    )
                }
                AnimatedVisibility(
                    visible = startMenuVisible,
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    StartMenu(
                        exitCallback = exitCallback,
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .fillMaxWidth(0.4f)
                            .offset(y = 50.dp)
                    )
                }
                AnimatedVisibility(
                    visible = deviceManagerVisible,
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    DeviceManager(
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .fillMaxWidth(0.5f)
                            .offset {
                                IntOffset(deviceManagerOffset.x.toInt(), deviceManagerOffset.y.toInt())
                            },
                        onClose = {
                            deviceManagerVisible = !deviceManagerVisible
                        },
                        onDrag = { deviceManagerOffset += it }
                    )
                }
            }
        } else {
            LoginPanel(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painter = painterResource("login_page_image.png"),
                        contentScale = ContentScale.FillBounds
                    ),
                loginCallback = { login = true }
            )
        }
    }
}

fun main() = application {
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        initMainViewModel()
    }
    coroutineScope.launch {
        initUserViewModel()
    }
    coroutineScope.launch {
        initDevicesManagerViewModel()
    }

    Window(onCloseRequest = ::exitApplication) {
        App(exitCallback = ::exitApplication)
    }
}

private fun initMainViewModel() {
    MainViewModel.getIconsOnToolsPanel()
    MainViewModel.getIconsOnDesktop()
    MainViewModel.getIconsOnStartMenu()
}

private fun initUserViewModel() {
    UserViewModel.setUsers(UserViewModel.getDefaultsUsers())
    UserViewModel.setUser(UserViewModel.getOneUser())
}

private fun initDevicesManagerViewModel() {
    DeviceManagerViewModel.setDevices()
}
