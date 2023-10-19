package presenter.compose

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import models.IconObjectToolsPanel
import presenter.MainViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DesktopBox(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val icons by viewModel.iconsOnDesktop.collectAsState()

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
                                    viewModel.addIconOnToolsPanel(
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

                                    }
                                )
                                .onDrag {
                                    iconOffset += it
                                }
                        ) {
                            Image(
                                painter = painterResource("icon_win11.png"),
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