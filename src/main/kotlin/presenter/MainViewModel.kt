package presenter

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
    fun getIconsOnDesktop() {
        _iconsOnDesktop.value = mutableListOf<IconObjectDesktop>().apply {
            add(
                IconObjectDesktop(
                    name = "Yandex",
                    path = "/usr/bin/yandex-browser-stable",
                    iconPath = "/usr/share/applications/yandex-browser",
                    x = 0f,
                    y = 0f
                )
            )
            add(
                IconObjectDesktop(
                    name = "Intellij IDEA",
                    path = "/home/lord-artem/.local/share/JetBrains/Toolbox/apps/intellij-idea-ultimate/bin/idea.sh",
                    iconPath = "/usr/share/applications/kcm_clock.desktop",
                    x = 100f,
                    y = 100f
                )
            )
            add(
                IconObjectDesktop(
                    name = "discord",
                    path = "/usr/share/discord/Discord",
                    iconPath = "/usr/share/applications/discord",
                    x = 100f,
                    y = 250f
                )
            )
        }
    }

    // Сохранить список приложений на рабочем столе
    fun saveIconsOnDesktop() {
        TODO()
    }

    // Получить список приложений на панели задач
    fun getIconsOnToolsPanel() {
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
    fun saveIconsOnToolsPanel() {
        TODO()
    }

    // Добавить иконку на панель задач
    fun addIconOnToolsPanel(newIcon: IconObjectToolsPanel) {
        val newList = mutableListOf<IconObjectToolsPanel>().apply {
            addAll(_iconsOnToolsPanel.value)
            add(newIcon)
        }
        _iconsOnToolsPanel.value = newList
    }

    fun deleteIconFromToolsPanel(icon: IconObjectToolsPanel) {
        val newList = mutableListOf<IconObjectToolsPanel>().apply {
            addAll(_iconsOnToolsPanel.value)
            remove(icon)
        }
        _iconsOnToolsPanel.value = newList
    }


    // Получить список приложений в меню пуск
    fun getIconsOnStartMenu() {
        TODO()
    }

    // Сохранить список приложений в меню пуск
    fun saveIconsOnStartMenu() {
        TODO()
    }
}