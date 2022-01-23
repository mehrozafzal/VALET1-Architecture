package com.mehroz.valet1_task.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mehroz.valet1_task.data.repository.FakeDeviceRepository
import com.mehroz.valet1_task.MainCoroutineRule
import com.mehroz.valet1_task.core.Status
import com.mehroz.valet1_task.data.local.Device
import com.mehroz.valet1_task.domain.DevicesUseCase
import com.mehroz.valet1_task.util.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel

    private lateinit var devicesUseCase: DevicesUseCase

    @Before
    fun setup() {
        devicesUseCase = DevicesUseCase(FakeDeviceRepository(), null)
        viewModel = MainViewModel(devicesUseCase)
    }

    @Test
    fun `insert device null object, returns error`() {
        viewModel.insertDeviceInDb(null)
        val value = viewModel.insertDeviceStatus.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert device non null object returns success `() {
        val device = Device(
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

        viewModel.insertDeviceInDb(device)

        val value = viewModel.insertDeviceStatus.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `remove device with empty id return null`() {
        viewModel.removeDeviceInDb(null)

        val value = viewModel.removeDeviceStatus.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `remove device with non empty id return success`() {
        viewModel.removeDeviceInDb(1)

        val value = viewModel.removeDeviceStatus.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    
    @Test
    fun `get all devices return error`() {
        val device: Device? = null
        viewModel.insertDeviceInDb(device)
        viewModel.fetchDevices()
        runBlocking {
            val devices = viewModel.devicesStateFlow.value
            assertThat(device).isNull()
            assertThat(devices.data!!.size).isEqualTo(0)
        }
    }

    @Test
    fun `get single device returns success`() {
        val device = Device(
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
        viewModel.insertDeviceInDb(device)
        viewModel.getDeviceFromDb(device.id)
        runBlocking {
            val devices = viewModel.deviceStateFlow.value
            assertThat(devices).isNotNull()
            assertThat(device).isEqualTo(devices.data)
        }
    }

    @Test
    fun `get single device returns error`() {
        val device: Device? = null
        viewModel.insertDeviceInDb(device)
        viewModel.getDeviceFromDb(0)
        runBlocking {
            val myDevice = viewModel.deviceStateFlow.value
            assertThat(myDevice.data!!.id).isEqualTo(1)
        }
    }
}