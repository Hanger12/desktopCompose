package models

data class DeviceGroup(
    val name: String,
    val type: DeviceType,
    val devices: List<Device>
)

data class Device(
    val name: String,
)

enum class DeviceType {
    Bluetooth,
    Audio,
    VGA,
    RAM,
    Keyboard,
    Mouse,
    Disk,
    USB,
    Kernel,
    Network,
    Other
}
