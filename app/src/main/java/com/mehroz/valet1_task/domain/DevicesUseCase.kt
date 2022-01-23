package com.mehroz.valet1_task.domain

import com.mehroz.valet1_task.data.local.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DevicesUseCase @Inject constructor(private val devicesRepository: DevicesRepository) {

    suspend fun fetchDevices(): Flow<List<Device>> = flow {
        emit(devicesRepository.fetchMyDevices())
    }.flowOn(Dispatchers.IO)

    suspend fun getDeviceFromDb(deviceID: Int): Flow<Device?> = flow {
        emit(devicesRepository.getDevice(deviceID))
    }.flowOn(Dispatchers.IO)

    suspend fun getAllDevicesFromDb(): Flow<List<Device>> = flow {
        emit(devicesRepository.getAllDevice())
    }.flowOn(Dispatchers.IO)

    suspend fun insertDevice(device: Device) = devicesRepository.insertDevice(device)
    suspend fun removeDevice(deviceID: Int) = devicesRepository.removeDevice(deviceID)

}