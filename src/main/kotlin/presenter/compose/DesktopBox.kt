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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import models.IconObjectStartMenu
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
                            ContextMenuItem("Открыть") {
                                coroutineScope.launch {
                                    ProcessBuilderCommands.startProcess(icon.path)
                                }
                            },
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
                            ContextMenuItem("Закрепить на начальном экране") {
                                coroutineScope.launch {
                                    MainViewModel.addIconOnStartMenu(
                                        newIcon = IconObjectStartMenu(
                                            name = icon.name,
                                            path = icon.path,
                                            iconPath = icon.iconPath
                                        )
                                    )
                                }
                            },
                            ContextMenuItem("Вырезать") {},
                            ContextMenuItem("Копировать") {},
                            ContextMenuItem("Создать ярлык") {},
                            ContextMenuItem("Удалить") {
                                MainViewModel.deleteIconFromDesktop(icon)
                            },
                            ContextMenuItem("Переименовать") {},
                            ContextMenuItem("Свойства") {}
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
                            // Патч к картинке
                            val iconFile = File("/usr/share/icons/BeautyLine/apps/scalable/${icon.iconPath}.svg")
                            Image(
                                painter = loadSvgPainter(
                                    inputStream = iconFile.inputStream(),
                                    density = Density(1f)
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                            )
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