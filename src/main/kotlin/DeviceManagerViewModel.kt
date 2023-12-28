import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import models.DeviceGroup
import models.DeviceType

object DeviceManagerViewModel {
    private var _devices = MutableStateFlow<List<DeviceGroup>>(emptyList())
    val devices = _devices.asStateFlow()

    fun setDevices() {
        val groups = mutableListOf<DeviceGroup>().apply {
            add(ProcessBuilderCommands.getDeviceGroup(DeviceType.Bluetooth))
            add(ProcessBuilderCommands.getDeviceGroup(DeviceType.Audio))
            add(ProcessBuilderCommands.getDeviceGroup(DeviceType.VGA))
            add(ProcessBuilderCommands.getDeviceGroup(DeviceType.Disk))
            add(ProcessBuilderCommands.getDeviceGroup(DeviceType.USB))
            add(ProcessBuilderCommands.getDeviceGroup(DeviceType.Kernel))
            add(ProcessBuilderCommands.getDeviceGroup(DeviceType.Network))
        }
        _devices.value = groups
    }
}