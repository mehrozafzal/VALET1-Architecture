package com.mehroz.valet1_task.presentation

import com.mehroz.valet1_task.data.remote.ApiService
import com.mehroz.valet1_task.data.repository.DevicesRepositoryImpl
import com.mehroz.valet1_task.domain.DevicesRepository
import com.mehroz.valet1_task.domain.DevicesUseCase
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
    fun provideDevicesUseCase(devicesRepository: DevicesRepository) = DevicesUseCase(devicesRepository)

    @Provides
    @ViewModelScoped
    fun provideDevicesRepository(
        apiService: ApiService
    ): DevicesRepository {
        return DevicesRepositoryImpl(apiService)
    }
}