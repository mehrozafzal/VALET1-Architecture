package com.mehroz.valet1_task.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Query("SELECT * FROM devices")
    fun getAllFavoriteDevices(): List<Device>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(device: Device)

    @Query("DELETE FROM devices WHERE id = :id")
    suspend fun remove(id: Int)

    @Query("SELECT * FROM devices WHERE id = :id")
    fun getDevice(id: Int): Device
}