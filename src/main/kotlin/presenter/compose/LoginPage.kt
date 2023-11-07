package presenter.compose

import UserViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginPanel(
    modifier: Modifier = Modifier,
    loginCallback: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            UsersList(modifier = Modifier.fillMaxSize())
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            UserPanel(
                modifier = Modifier.fillMaxSize(),
                loginCallback = loginCallback
            )
        }
    }
}

@Composable
fun UserPanel(
    modifier: Modifier = Modifier,
    loginCallback: () -> Unit
) {
    val user by UserViewModel.user.collectAsState()
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user != null){
            Image(
                painter = if (user?.imagePath == "") painterResource("image_user_default.png") else painterResource(user!!.imagePath),
                contentDescription = "user image",
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .clip(CircleShape),
            )
            Text(
                text = user?.name ?: "",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(vertical = 10.dp)
            )
            TextField(
                value = password,
                onValueChange = { newValue ->
                    password = newValue
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White
                ),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                ),
                modifier = Modifier.onKeyEvent {
                    if (it.key == Key.Enter && password == user?.password) {
                        loginCallback.invoke()
                        true
                    } else {
                        false
                    }
                },
                singleLine = true
            )
            Text(
                text = "Я забыл пароль",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                ),
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Text(
                text = "Sign-in options",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                ),
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UsersList(modifier: Modifier = Modifier) {
    val users by UserViewModel.usersList.collectAsState()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom
    ) {
        items(
            items = users,
            key = { user -> user.id }
        ) { user ->
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .onClick {
                        UserViewModel.setUser(user)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = if (user.imagePath == "") painterResource("image_user_default.png") else painterResource(user.imagePath),
                    contentDescription = "user image",
                    modifier = Modifier
                        .height(50.dp)
                        .clip(CircleShape),
                )
                Text(
                    text = user.name,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}