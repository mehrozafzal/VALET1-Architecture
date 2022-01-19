package com.mehroz.valet1_task.presentation

import androidx.lifecycle.viewModelScope
import com.mehroz.valet1_task.base.BaseViewModel
import com.mehroz.valet1_task.core.Resource
import com.mehroz.valet1_task.data.remote.response.DevicesResponse
import com.mehroz.valet1_task.data.remote.response.DevicesResponseItem
import com.mehroz.valet1_task.domain.DevicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val devicesUseCase: DevicesUseCase
) : BaseViewModel() {

    private val devicesStateFlow = MutableStateFlow<Resource<MutableList<DevicesResponseItem>>>(
        Resource.loading(
            null
        )
    )

    fun fetchDevices() {
        viewModelScope.launch {
            devicesUseCase.fetchDevices()
                .catch { e ->
                    devicesStateFlow.value = (Resource.error(e.toString(), null))
                }
                .collect {
                    devicesStateFlow.value = (Resource.success(it.data))
                }
        }
    }

    fun getDevicesStateFlow(): StateFlow<Resource<MutableList<DevicesResponseItem>>> {
        return devicesStateFlow
    }
}