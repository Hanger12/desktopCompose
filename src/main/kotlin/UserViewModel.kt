import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import models.User

object UserViewModel {

    // Пользователь
    private var _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private var _usersList = MutableStateFlow<List<User>>(emptyList())
    val usersList = _usersList.asStateFlow()

    fun setUser(loginUser: User) {
        _user.value = loginUser
    }

    fun setUsers(users: List<User>) {
        _usersList.value = users
    }

    fun getOneUser() = _usersList.value[0]

    fun getDefaultsUsers(): List<User> {
        val users = mutableListOf<User>().apply {
            add(
                User(
                id = "1",
                name = "Юрий Витальевич",
                    password = "12345",
                imagePath = "image_user_default.png"
                )
            )
            add(
                User(
                    id = "2",
                    name = "King_Pro100_win",
                    password = "1212356",
                    imagePath = "image_user2.png"
                )
            )
        }
        return users.toList()
    }
}