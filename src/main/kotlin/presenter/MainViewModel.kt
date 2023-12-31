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
                    name = "Opera",
                    path = "opera",
                    iconPath = "opera",
                    x = 10f,
                    y = 10f
                )
            )
            add(
                IconObjectDesktop(
                    name = "Intellij IDEA",
                    path = "/usr/bin/idea",
                    iconPath = "intellij_idea",
                    x = 110f,
                    y = 10f
                )
            )
            add(
                IconObjectDesktop(
                    name = "discord",
                    path = "/usr/share/discord/Discord",
                    iconPath = "discord",
                    x = 210f,
                    y = 10f
                )
            )
            add(
                IconObjectDesktop(
                    name = "File Manager",
                    path = "dolphin",
                    iconPath = "system-file-manager",
                    x = 310f,
                    y = 10f
                )
            )
        }
    }

    fun deleteIconFromDesktop(icon: IconObjectDesktop) {
        val list = mutableListOf<IconObjectDesktop>().apply {
            addAll(_iconsOnDesktop.value)
            remove(icon)
        }
        _iconsOnDesktop.value = list.toList()
    }

    // Сохранить список приложений на рабочем столе
    fun saveIconsOnDesktop() {
        TODO()
    }

    // Получить список приложений на панели задач
    fun getIconsOnToolsPanel() {
        _iconsOnToolsPanel.value = mutableListOf<IconObjectToolsPanel>().apply {

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
        _iconsOnStartMenu.value = mutableListOf<IconObjectStartMenu>().apply {
            add(
                IconObjectStartMenu(
                    name = "Opera",
                    path = "opera",
                    iconPath = "opera",
                )
            )
            add(
                IconObjectStartMenu(
                    name = "Intellij IDEA",
                    path = "/usr/bin/idea",
                    iconPath = "intellij_idea",
                )
            )
            add(
                IconObjectStartMenu(
                    name = "discord",
                    path = "/usr/share/discord/Discord",
                    iconPath = "discord",
                )
            )
            add(
                IconObjectStartMenu(
                    name = "File Manager",
                    path = "dolphin",
                    iconPath = "system-file-manager",
                )
            )
        }
    }

    fun addIconOnStartMenu(newIcon: IconObjectStartMenu) {
        val newList = mutableListOf<IconObjectStartMenu>().apply {
            addAll(_iconsOnStartMenu.value)
            add(newIcon)
        }
        _iconsOnStartMenu.value = newList
    }

    fun deleteIconFromStartMenu(icon: IconObjectStartMenu) {
        val newList = mutableListOf<IconObjectStartMenu>().apply {
            addAll(_iconsOnStartMenu.value)
            remove(icon)
        }
        _iconsOnStartMenu.value = newList
    }

    // Сохранить список приложений в меню пуск
    fun saveIconsOnStartMenu() {
        TODO()
    }
}