import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import models.IconObjectDesktop
import models.IconObjectStartMenu
import models.IconObjectToolsPanel

object MainViewModel {
    // Список приложений на рабочем столе
    private var _iconsOnDesktop = MutableStateFlow<List<IconObjectDesktop>>(emptyList())
    val iconsOnDesktop = _iconsOnDesktop.asStateFlow()

    // Список приложений на панели задач
    private var _iconsOnToolsPanel = MutableStateFlow<List<IconObjectToolsPanel>>(emptyList())
    val iconsOnToolsPanel = _iconsOnToolsPanel.asStateFlow()

    // Список приложений в меню пуск
    private var _iconsOnStartMenu = MutableStateFlow<List<IconObjectStartMenu>>(emptyList())
    val iconsOnStartMenu = _iconsOnStartMenu.asStateFlow()


    // Получить список приложений на рабочем столе
    suspend fun getIconsOnDesktop() {
        TODO()
    }

    // Сохранить список приложений на рабочем столе
    suspend fun saveIconsOnDesktop() {
        TODO()
    }

    // Получить список приложений на панели задач
    suspend fun getIconsOnToolsPanel() {
        _iconsOnToolsPanel.value = mutableListOf<IconObjectToolsPanel>().apply {
            for (i in 0..3) {
                add(
                    IconObjectToolsPanel(
                        path = "",
                        iconPath = "",
                    )
                )
            }
        }
    }

    // Сохранить список приложений на панели задач
    suspend fun saveIconsOnToolsPanel() {
        TODO()
    }


    // Получить список приложений в меню пуск
    suspend fun getIconsOnStartMenu() {
        TODO()
    }

    // Сохранить список приложений в меню пуск
    suspend fun saveIconsOnStartMenu() {
        TODO()
    }
}