package presenter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import presenter.compose.DesktopBox
import presenter.compose.StartMenu
import presenter.compose.ToolsPanel

@Composable
fun App(viewModel: MainViewModel) {
    MaterialTheme {
        var startMenuVisible by remember { mutableStateOf(false) }

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
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Blue),
                    )
                }
                ToolsPanel(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize(),
                    startMenuVisibleCallback = { startMenuVisible = !startMenuVisible }
                )
            }
            AnimatedVisibility(
                visible = startMenuVisible,
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                StartMenu(
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .fillMaxWidth(0.5f)
                )
            }
        }
    }
}

fun main() = application {
    val viewModel = MainViewModel
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        init(viewModel)
    }


    Window(onCloseRequest = ::exitApplication) {
        App(viewModel)
    }
}

private fun init(viewModel: MainViewModel) {
    viewModel.getIconsOnToolsPanel()
    viewModel.getIconsOnDesktop()
    // viewModel.getIconsOnStartMenu()
}
