package com.mehroz.valet1_task.data.repository

import com.mehroz.valet1_task.data.local.Device
import com.mehroz.valet1_task.domain.DevicesRepository
import com.mehroz.valet1_task.utils.Constants.MODE_KEY
import com.mehroz.valet1_task.utils.SharedPrefs

class FakeDeviceRepository : DevicesRepository {

    private val devices = mutableListOf<Device>()

    override suspend fun fetchMyDevices(): List<Device> {
        return devices
    }

    override suspend fun insertDevice(device: Device) {
        devices.add(device)
    }

    override suspend fun removeDevice(deviceID: Int) {
        for (device in devices) {
            if (device.id == deviceID)
                devices.remove(device)
        }
    }

    override suspend fun getDevice(deviceID: Int): Device {
        for (device in devices) {
            if (device.id == deviceID)
                return device
        }
        return Device(
            "Available",
            "Sensor",
            "Vivo Y20s Price in Pakistan 2022 is Rs. 30,999/-",
            0,
            "PKR",
            "https://i0.wp.com/newmobiles.com.pk/wp-content/uploads/2021/06/Vivo-Y20s-new-mobiles-400x400.jpg",
            "Vivo",
            1,
            false
        )
    }

    override suspend fun getAllDevice(): List<Device> {
        if (!devices.isNullOrEmpty())
            return devices
        else
            return listOf(
                Device(
                    "Available",
                    "Sensor",
                    "Vivo Y20s Price in Pakistan 2022 is Rs. 30,999/-",
                    0,
                    "PKR",
                    "https://i0.wp.com/newmobiles.com.pk/wp-content/uploads/2021/06/Vivo-Y20s-new-mobiles-400x400.jpg",
                    "Vivo",
                    1,
                    false
                )
            )
    }

}