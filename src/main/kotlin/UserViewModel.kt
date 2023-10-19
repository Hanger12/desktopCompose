import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import models.User

object UserViewModel {

    // Пользователь
    private var _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun setUser(loginUser: User) {
        _user.value = loginUser
    }

    fun getDefaultUser() =
        User(
            id = "1",
            name = "Юрий Витальевич",
            imagePath = ""
        )
}