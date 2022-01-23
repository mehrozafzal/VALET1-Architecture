package com.mehroz.valet1_task.domain

import com.mehroz.valet1_task.data.local.Device


interface DevicesRepository {
    suspend fun fetchMyDevices(): List<Device>
    suspend fun insertDevice(device: Device)
    suspend fun removeDevice(deviceID: Int)
    suspend fun getDevice(deviceID: Int): Device
    suspend fun getAllDevice(): List<Device>
}