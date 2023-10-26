import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import models.Device
import models.DeviceGroup
import models.DeviceType

object DeviceManagerViewModel {
    private var _devices = MutableStateFlow<List<DeviceGroup>>(emptyList())
    val devices = _devices.asStateFlow()

    fun setTestDevices() {
        val list = mutableListOf<Device>().apply {
            add(Device("Test1"))
            add(Device("Test2"))
        }

        val list2 = mutableListOf<DeviceGroup>().apply {
            add(DeviceGroup(
                name = "Test1",
                type = DeviceType.Audio,
                devices = list
            ))
            add(DeviceGroup(
                name = "Test2",
                type = DeviceType.Audio,
                devices = list
            ))
            add(DeviceGroup(
                name = "Test3",
                type = DeviceType.Audio,
                devices = list
            ))
            add(DeviceGroup(
                name = "Test4",
                type = DeviceType.Audio,
                devices = list
            ))
            add(DeviceGroup(
                name = "Test5",
                type = DeviceType.Audio,
                devices = list
            ))
            add(DeviceGroup(
                name = "Test6",
                type = DeviceType.Audio,
                devices = list
            ))
            add(DeviceGroup(
                name = "Test7",
                type = DeviceType.Audio,
                devices = list
            ))
            add(DeviceGroup(
                name = "Test8",
                type = DeviceType.Audio,
                devices = list
            ))
        }

        _devices.value = list2
    }
}