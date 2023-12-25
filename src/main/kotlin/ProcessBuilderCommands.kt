import androidx.compose.ui.text.toLowerCase
import models.Device
import models.DeviceGroup
import models.DeviceType
import java.util.*

class ProcessBuilderCommands {

    companion object {
        fun startProcess(path: String) {
            val processBuilder = ProcessBuilder()

            processBuilder.command(path)
            processBuilder.start()
        }

        fun getDeviceGroup(deviceType: DeviceType): DeviceGroup {
            val devices = mutableListOf<Device>()

            var type = ""
            var name = ""
            when (deviceType) {
                DeviceType.Audio -> {
                    type = "Audio"
                    name = "Аудиовходы и аудиовыходы"
                }

                DeviceType.Bluetooth -> {
                    type = "Bluetooth"
                    name = "Bluetooth"
                }

                DeviceType.VGA -> {
                    type = "VGA"
                    name = "Видеоадаптеры"
                }

                DeviceType.Keyboard -> {
                    type = ""
                    name = "Клавиатуры"
                }

                DeviceType.Mouse -> {
                    type = ""
                    name = "Мыши"
                }

                DeviceType.Disk -> {
                    type = "SATA"
                    name = "Дисковые устройства"
                }

                DeviceType.USB -> {
                    type = "USB"
                    name = "Контроллеры USB"
                }

                DeviceType.Kernel -> {
                    type = ""
                    name = "Процессоры"
                }

                DeviceType.Network -> {
                    type = "Network\\|Ethernet"
                    name = "Сетевые адаптеры"
                }

                else -> ""
            }

            if (deviceType == DeviceType.Kernel) {
                val process = ProcessBuilder("hwinfo", "--short", "--cpu").start()
                process.inputStream.reader(Charsets.UTF_8).use {
                    val result = it.readText().split("\n")
                    result.forEach { param ->
                        if (param != "cpu:" && param.isNotEmpty()) {
                            devices.add(Device(param.trim()))
                        }
                    }
                }
            } else {
                val builders = mutableListOf<ProcessBuilder>().apply {
                    add(ProcessBuilder("lspci"))
                    add(ProcessBuilder("grep", type))
                }
                val process = ProcessBuilder.startPipeline(builders)
                val last = process[1]
                last.inputStream.reader(Charsets.UTF_8).use {
                    val result = it.readText().split("\n")
                    result.forEach { param ->
                        if (param.isNotEmpty()) {
                            devices.add(Device(param.trim()))
                        }
                    }
                }

            }

            return DeviceGroup(
                name = name,
                type = deviceType,
                devices = devices
            )
        }

        fun terminalCommand(value: String): String {
            val command = value.split(' ')
            return when (command[0].trim().lowercase(Locale.ENGLISH)) {
                "help" -> {
                    StringBuilder()
                        .append("tasklist (список активных процессов)\n")
                        .append("ipconfig (текущие значения конфигурации сети)\n")
                        .append("dir (список файлов и подкаталогов каталога)\n")
                        .append("copy [file] [path] (копирование файла)\n")
                        .append("move [file] [path] (переместить файл)\n")
                        .append("del [file] (удаление файла)\n")
                        .append("makedir [path] (создание папки)\n")
                        .append("systeminfo (нформация о конфигурации компьютера)\n")
                        .append("date (текущая дата)\n")
                        .append("hostname (имя компьютера)\n")
                        .toString()
                }

                "tasklist" -> startCommand(commandList = listOf("ps"))

                "ipconfig" -> startCommand(commandList = listOf("ip", "a"))

                "dir" -> startCommand(commandList = listOf("ls"))

                "copy" -> {
                    if (command.size == 3) {
                        startCommand(commandList = listOf("cp", command[1], command[2]))
                    } else {
                        "Ошибка 0x00000057 произошла: Параметр задан неверно."
                    }
                }

                "move" -> {
                    if (command.size == 3) {
                        startCommand(commandList = listOf("mv", command[1], command[2]))
                    } else {
                        "Ошибка 0x00000057 произошла: Параметр задан неверно."
                    }
                }

                "del" -> {
                    if (command.size == 2) {
                        startCommand(commandList = listOf("rm", command[1]))
                    } else {
                        "Ошибка 0x00000057 произошла: Параметр задан неверно."
                    }
                }

                "makedir" -> {
                    if (command.size == 2) {
                        startCommand(commandList = listOf("mkdir", command[1]))
                    } else {
                        "Ошибка 0x00000057 произошла: Параметр задан неверно."
                    }
                }

                "systeminfo" -> startCommand(commandList = listOf("lscpu"))

                "date" -> startCommand(commandList = listOf("date"))

                "hostname" -> startCommand(commandList = listOf("hostname"))

                else -> "${command[0]} не является внутренней или внешней командой, исполняемой программой или пакетным файлом."
            }
        }

        private fun startCommand(commandList: List<String>): String {
            val process = ProcessBuilder(commandList).start()
            process.inputStream.reader(Charsets.UTF_8).use {
                return it.readText()
                /*val strBuilder = StringBuilder()
                result.forEach { param ->
                    if (param.isNotEmpty()) {
                        strBuilder.append(param.trim())
                    }
                }
                return strBuilder.toString()*/
            }
        }
    }
}