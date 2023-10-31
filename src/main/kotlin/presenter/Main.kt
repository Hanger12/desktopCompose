package presenter

import DeviceManagerViewModel
import UserViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import presenter.compose.DesktopBox
import presenter.compose.DeviceManager
import presenter.compose.StartMenu
import presenter.compose.ToolsPanel

@Composable
fun App(
    exitCallback: () -> Unit,
) {
    MaterialTheme {
        var startMenuVisible by remember { mutableStateOf(false) }
        var deviceManagerVisible by remember { mutableStateOf(false) }

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
                            .background(color = Color.Blue),
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
                        .fillMaxWidth(0.7f)
                        .offset(y = 50.dp),
                    onClose = {
                        deviceManagerVisible = !deviceManagerVisible
                    }
                )
            }
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
    // viewModel.getIconsOnStartMenu()
}

private fun initUserViewModel() {
    UserViewModel.setUser(UserViewModel.getDefaultUser())
}

private fun initDevicesManagerViewModel() {
    DeviceManagerViewModel.setTestDevices()
}
