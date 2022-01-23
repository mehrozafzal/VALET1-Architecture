package com.mehroz.valet1_task.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mehroz.valet1_task.base.BaseViewModel
import com.mehroz.valet1_task.core.Resource
import com.mehroz.valet1_task.data.local.Device
import com.mehroz.valet1_task.domain.DevicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val devicesUseCase: DevicesUseCase
) : BaseViewModel() {

    private val _deviceDetailObserver = MutableLiveData<Device>()
    val deviceDetailObserver = _deviceDetailObserver

    private val _devicesStateFlow = MutableStateFlow<Resource<List<Device>>>(Resource.loading(null))
    val devicesStateFlow = _devicesStateFlow.asStateFlow()

    private val _dbDevicesStateFlow = MutableStateFlow<Resource<List<Device>>>(Resource.loading(null))
    val dbDevicesStateFlow = _dbDevicesStateFlow.asStateFlow()

    private val _deviceStateFlow = MutableStateFlow<Resource<Device>>(Resource.loading(null))
    val deviceStateFlow = _deviceStateFlow.asStateFlow()

    private val _insertDeviceStatus = MutableLiveData<Resource<Device>>()
    val insertDeviceStatus: LiveData<Resource<Device>> = _insertDeviceStatus

    private val _removeDeviceStatus = MutableLiveData<Resource<Device>>()
    val removeDeviceStatus: LiveData<Resource<Device>> = _removeDeviceStatus


    fun fetchDevices() {
        viewModelScope.launch {
            devicesUseCase.fetchDevices()
                .catch { e ->
                    _devicesStateFlow.value = (Resource.error(e.toString(), null))
                }
                .collectLatest {
                    _devicesStateFlow.value = (Resource.success(it))
                }
        }
    }

    fun insertDeviceInDb(deviceItem: Device?) {
        if (deviceItem == null) {
            _insertDeviceStatus.postValue(Resource.error("Device cannot be null", null))
            return
        }
        viewModelScope.launch {
            devicesUseCase.insertDevice(deviceItem)
            _insertDeviceStatus.postValue(Resource.success(deviceItem))
        }
    }

    fun removeDeviceInDb(deviceID: Int?) {
        if (deviceID == null) {
            _removeDeviceStatus.postValue(Resource.error("Device id cannot be null", null))
            return
        }
        viewModelScope.launch {
            devicesUseCase.removeDevice(deviceID)
            _removeDeviceStatus.postValue(Resource.success(null))
        }
    }

    fun getDeviceFromDb(deviceID: Int) {
        viewModelScope.launch {
            devicesUseCase.getDeviceFromDb(deviceID)
                .catch { e ->
                    _deviceStateFlow.value = (Resource.error(e.toString(), null))
                }
                .collectLatest {
                    _deviceStateFlow.value = (Resource.success(it))
                }
        }
    }

    fun getAllDeviceFromDb() {
        viewModelScope.launch {
            devicesUseCase.getAllDevicesFromDb()
                .catch { e ->
                    _dbDevicesStateFlow.value = (Resource.error(e.toString(), null))
                }
                .collect {
                    if (!it.isNullOrEmpty())
                        _dbDevicesStateFlow.value = (Resource.success(it))
                    else
                        _dbDevicesStateFlow.value = (Resource.error("", null))
                }
        }
    }

}