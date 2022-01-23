package com.mehroz.valet1_task.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class DeviceDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var dao: DeviceDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.deviceDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertDeviceItem() = runBlockingTest {

        val deviceItem = Device("Available","Sensor","Vivo Y20s Price in Pakistan 2022 is Rs. 30,999/-",0,"PKR","https://i0.wp.com/newmobiles.com.pk/wp-content/uploads/2021/06/Vivo-Y20s-new-mobiles-400x400.jpg","Vivo",1,false)
        dao.insert(deviceItem)

        val allDevices = dao.getAllFavoriteDevices()

        assertThat(allDevices).contains(deviceItem)
    }

    @Test
    fun deleteDeviceItem() = runBlockingTest {
        val deviceItem = Device("Available","Sensor","Vivo Y20s Price in Pakistan 2022 is Rs. 30,999/-",0,"PKR","https://i0.wp.com/newmobiles.com.pk/wp-content/uploads/2021/06/Vivo-Y20s-new-mobiles-400x400.jpg","Vivo",1,false)
        dao.insert(deviceItem)
        dao.remove(deviceItem.id)

        val allDevices = dao.getAllFavoriteDevices()

        assertThat(allDevices).doesNotContain(deviceItem)
    }

    @Test
    fun getDeviceItem() = runBlockingTest {
        val deviceItem = Device("Available","Sensor","Vivo Y20s Price in Pakistan 2022 is Rs. 30,999/-",0,"PKR","https://i0.wp.com/newmobiles.com.pk/wp-content/uploads/2021/06/Vivo-Y20s-new-mobiles-400x400.jpg","Vivo",1,false)
        dao.insert(deviceItem)

        val device = dao.getDevice(deviceItem.id)

        assertThat(device).isEqualTo(deviceItem)
    }
}