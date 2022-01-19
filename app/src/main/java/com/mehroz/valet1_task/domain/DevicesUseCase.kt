package com.mehroz.valet1_task.domain

import com.mehroz.valet1_task.core.Resource
import com.mehroz.valet1_task.data.remote.response.DevicesResponse
import com.mehroz.valet1_task.data.remote.response.DevicesResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DevicesUseCase @Inject constructor(private val devicesRepository: DevicesRepository) {
    suspend fun fetchDevices(): Flow<Resource<MutableList<DevicesResponseItem>>> = flow {
        emit(devicesRepository.fetchMyDevices())
    }.flowOn(Dispatchers.IO)
}