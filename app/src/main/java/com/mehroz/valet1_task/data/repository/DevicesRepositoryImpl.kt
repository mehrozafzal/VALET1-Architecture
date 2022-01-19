package com.mehroz.valet1_task.data.repository

import com.mehroz.valet1_task.core.Resource
import com.mehroz.valet1_task.data.remote.ApiService
import com.mehroz.valet1_task.data.remote.response.DevicesResponse
import com.mehroz.valet1_task.data.remote.response.DevicesResponseItem
import com.mehroz.valet1_task.domain.DevicesRepository
import javax.inject.Inject

class DevicesRepositoryImpl(
    private val apiService: ApiService
) :
    DevicesRepository {
    override suspend fun fetchMyDevices(): Resource<MutableList<DevicesResponseItem>> = apiService.getDevices()
}