package com.mehroz.valet1_task.di

import com.mehroz.valet1_task.data.local.DeviceDao
import com.mehroz.valet1_task.data.remote.ApiService
import com.mehroz.valet1_task.data.repository.DevicesRepositoryImpl
import com.mehroz.valet1_task.domain.DevicesRepository
import com.mehroz.valet1_task.domain.DevicesUseCase
import com.mehroz.valet1_task.utils.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelDeviceModule {
    @Provides
    @ViewModelScoped
    fun provideDevicesUseCase(devicesRepository: DevicesRepository, sharedPrefs: SharedPrefs) =
        DevicesUseCase(devicesRepository, sharedPrefs)

    @Provides
    @ViewModelScoped
    fun provideDevicesRepository(
        apiService: ApiService,
        deviceDao: DeviceDao
    ): DevicesRepository {
        return DevicesRepositoryImpl(apiService, deviceDao)
    }
}