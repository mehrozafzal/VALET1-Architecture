package com.mehroz.valet1_task.domain

import com.mehroz.valet1_task.core.Resource
import com.mehroz.valet1_task.data.remote.response.DevicesResponse
import com.mehroz.valet1_task.data.remote.response.DevicesResponseItem


interface DevicesRepository {
    suspend fun fetchMyDevices(): Resource<MutableList<DevicesResponseItem>>
}