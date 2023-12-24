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
            for (x in command) {
                x.lowercase(Locale.ENGLISH)
            }
            return when (command[0]) {
                "help" -> {
                    "perfmon" +
                            "ipconfig" +
                            "dir" +
                            "" +
                            "" +
                            "" +
                            "" +
                            "" +
                            "" +
                            ""
                }

                "perfmon" -> startCommand(commandList = listOf("top"))

                "ipconfig" -> startCommand(commandList = listOf("ip", "a"))

                "dir" -> startCommand(commandList = listOf("ls"))

                // TODO

                else -> "$command не является внутренней или внешней командой, исполняемой программой или пакетным файлом."
            }
            //return "Ошибка 0x00000057 произошла: Параметр задан неверно."
        }

        private fun startCommand(commandList: List<String>): String {
            val builders = mutableListOf<ProcessBuilder>().apply {
                commandList.forEach { command ->
                    add(ProcessBuilder(command))
                }
            }
            val process = ProcessBuilder.startPipeline(builders)
            val last = process[1]
            last.inputStream.reader(Charsets.UTF_8).use {
                val result = it.readText().split("\n")
                val strBuilder = StringBuilder()
                result.forEach { param ->
                    if (param.isNotEmpty()) {
                        strBuilder.append(param.trim())
                    }
                }
                return strBuilder.toString()
            }
        }
    }
}