package presenter.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presenter.ToolsMain

@Composable
fun StartMenu(
    modifier: Modifier = Modifier
) {
    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(color = ToolsMain)
        ) {
            SearchField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            )
            Box(modifier = Modifier.fillMaxSize())
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
                    fontSize = 16.sp
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
                placeholder = { Text("Type here to search") }
            )
        }
    }
}