package com.mehroz.valet1_task.data.remote

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.mehroz.valet1_task.data.remote.ApiEndPoint.FETCH_DEVICES_URL
import com.mehroz.valet1_task.data.local.Device
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers


interface ApiService {
    @GET(FETCH_DEVICES_URL)
    @MockResponse(body = "devices.json")
    @Mock
    suspend fun getDevices(): List<Device>
}