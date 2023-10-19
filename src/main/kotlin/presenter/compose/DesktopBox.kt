package presenter.compose

import ProcessBuilderCommands
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import models.IconObjectToolsPanel
import presenter.MainViewModel
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DesktopBox(
    modifier: Modifier = Modifier
) {
    val icons by MainViewModel.iconsOnDesktop.collectAsState()

    Box(modifier = modifier) {
        val coroutineScope = rememberCoroutineScope()

        icons.forEach { icon ->
            Box {
                var iconOffset by remember { mutableStateOf(Offset(icon.x, icon.y)) }

                Box(modifier = Modifier
                    .offset {
                        IntOffset(iconOffset.x.toInt(), iconOffset.y.toInt())
                    })
                {
                    ContextMenuArea(items = {
                        listOf(
                            ContextMenuItem("Добавить на панель задач") {
                                coroutineScope.launch {
                                    MainViewModel.addIconOnToolsPanel(
                                        newIcon = IconObjectToolsPanel(
                                            path = icon.path,
                                            iconPath = icon.iconPath
                                        )
                                    )
                                }
                            },
                            ContextMenuItem("Что нибудь еще...") {

                            }
                        )
                    }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .onClick(
                                    matcher = PointerMatcher.mouse(PointerButton.Primary),
                                    onClick = {},
                                    onDoubleClick = {
                                        ProcessBuilderCommands.startProcess(icon.path)
                                    }
                                )
                                .onDrag {
                                    iconOffset += it
                                }
                        ) {
                            val iconFile = File("/usr/share/icons/BeautyLine/apps/scalable/${icon.iconPath}.svg")

                            try {
                                Image(
                                    painter = loadSvgPainter(
                                        inputStream = iconFile.inputStream(),
                                        density = Density(1f)
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp)
                                )
                            } catch (e: Exception) {
                                Image(
                                    painter = painterResource("icon_file.png"),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxHeight(0.8f)
                                )
                            }
                            Text(
                                text = icon.name,
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    ),
                            )
                        }
                    }
                }
            }
        }
    }
}