import models.Device
import models.DeviceGroup
import models.DeviceType

class ProcessBuilderCommands {

    companion object {
        fun startProcess(path: String) {
            val processBuilder = ProcessBuilder()

            processBuilder.command(path)
            processBuilder.start()
        }

        fun getDeviceGroup(deviceType: DeviceType) : DeviceGroup {
            val devices = mutableListOf<Device>()

            var type = ""
            var name = ""
            when(deviceType){
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
                DeviceType.Mouse ->{
                    type = ""
                    name = "Мыши"
                }
                DeviceType.Disk -> {
                    type = "SATA"
                    name = "Дисковые устройства"
                }
                DeviceType.USB ->{
                    type = "USB"
                    name = "Контроллеры USB"
                }
                DeviceType.Kernel ->{
                    type = ""
                    name = "Процессоры"
                }
                DeviceType.Network ->{
                    type = "'Network\\|Ethernet'"
                    name = "Сетевые адаптеры"
                }
                    else -> ""
                }

            val process = ProcessBuilder("grep", type).start()
            process.inputStream.reader(Charsets.UTF_8).use {
                devices.add(Device(it.readText()))
            }

            return DeviceGroup(
                name = name,
                type = deviceType,
                devices = devices
            )
        }
    }
}