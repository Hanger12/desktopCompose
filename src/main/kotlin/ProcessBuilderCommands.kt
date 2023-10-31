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

            return DeviceGroup(
                name = "",
                type = deviceType,
                devices = emptyList()
            )
        }
    }
}