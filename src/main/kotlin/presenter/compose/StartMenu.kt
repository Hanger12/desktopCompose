package presenter.compose

import UserViewModel
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import presenter.MainViewModel
import presenter.ToolsMain
import presenter.ToolsSecondary
import java.io.File

@Composable
fun StartMenu(
    exitCallback: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = ToolsMain)
                .fillMaxSize()
        ) {
            SearchField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            )
            PinedPanel(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f))
            UserPanel(
                exitCallback = exitCallback,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = ToolsSecondary)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PinedPanel(modifier: Modifier = Modifier) {
    val icons by MainViewModel.iconsOnStartMenu.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier.fillMaxSize().padding(top = 20.dp)
        ) {
            items(icons) { icon ->
                ContextMenuArea(items = {
                    listOf(
                        ContextMenuItem("Открепить") {
                            coroutineScope.launch {
                                MainViewModel.deleteIconFromStartMenu(icon)
                            }
                        }
                    )
                }) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.2f)
                            .onClick(
                                matcher = PointerMatcher.mouse(PointerButton.Primary),
                                onClick = { ProcessBuilderCommands.startProcess(icon.path) },
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val iconFile = File("/usr/share/icons/BeautyLine/apps/scalable/${icon.iconPath}.svg")
                        Image(
                            painter = loadSvgPainter(
                                inputStream = iconFile.inputStream(),
                                density = Density(1f)
                            ),
                            contentDescription = null
                        )
                        Text(
                            text = icon.name,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchField(modifier: Modifier = Modifier) {
    var searchValue by remember { mutableStateOf("") }

    Card(
        modifier = modifier,
        elevation = 10.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource("icon_search.png"),
                contentDescription = "search icon",
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .padding(start = 10.dp)
            )
            TextField(
                value = searchValue,
                onValueChange = { newText ->
                    searchValue = newText
                },
                textStyle = TextStyle(
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .fillMaxSize(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    placeholderColor = Color.Gray,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.White,
                ),
                placeholder = {
                    Text(
                        text = "Type here to search",
                        style = TextStyle(
                            fontSize = 14.sp
                        ),
                    )
                }
            )
        }
    }
}

@Composable
fun UserPanel(
    exitCallback: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user by UserViewModel.user.collectAsState()

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Image(
                    painter = if (user?.imagePath == "") painterResource("image_user_default.png") else painterResource(user!!.imagePath),
                    contentDescription = "user image",
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .clip(CircleShape),
                )
                Text(
                    text = user?.name ?: "User",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 10.dp)
                )
            }
            Image(
                painter = painterResource("icon_on_off.png"),
                contentDescription = "icon on off",
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .clickable { exitCallback.invoke() }
            )
        }
    }
}