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
            val type = when(deviceType){
                DeviceType.Audio -> "Audio"
                else -> ""
            }
            val process = ProcessBuilder("grep", type).start()
            process.inputStream.reader(Charsets.UTF_8).use {
                println(it.readText())
            }



            return DeviceGroup(
                name = "",
                type = deviceType,
                devices = emptyList()
            )
        }
    }
}