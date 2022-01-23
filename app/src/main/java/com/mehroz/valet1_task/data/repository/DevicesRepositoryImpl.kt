package com.mehroz.valet1_task.data.repository

import com.mehroz.valet1_task.data.local.DeviceDao
import com.mehroz.valet1_task.data.remote.ApiService
import com.mehroz.valet1_task.data.local.Device
import com.mehroz.valet1_task.domain.DevicesRepository

class DevicesRepositoryImpl(
    private val apiService: ApiService,
    private val deviceDao: DeviceDao) :
    DevicesRepository {
    override suspend fun fetchMyDevices(): List<Device> = apiService.getDevices()
    override suspend fun insertDevice(device: Device) {
        deviceDao.insert(device)
    }

    override suspend fun removeDevice(deviceID: Int) {
        deviceDao.remove(deviceID)
    }

    override suspend fun getDevice(deviceID: Int): Device {
        return deviceDao.getDevice(deviceID)
    }

    override suspend fun getAllDevice(): List<Device> {
        return deviceDao.getAllFavoriteDevices()
    }

}