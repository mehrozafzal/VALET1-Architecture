package com.mehroz.valet1_task.data.remote

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.infinum.retromock.Retromock
import com.google.common.reflect.TypeToken
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.mehroz.valet1_task.data.local.Device
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.mehroz.valet1_task.util.MockResponseFileReaderAndroidTest
import org.junit.After


@RunWith(AndroidJUnit4::class)
class ServiceTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val application = ApplicationProvider.getApplicationContext<Context>()
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("https://www.google.com/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val retroMock = Retromock.Builder()
            .retrofit(retrofit)
            .defaultBehavior { 0 }
            .defaultBodyFactory(application.assets::open)
            .build()
        apiService = retroMock.create(ApiService::class.java)
    }

    @Test
    fun fetchDevices_success() {
        val conversionType = object : TypeToken<List<Device>>() {}.type
        val response: List<Device> = Gson().fromJson(
            MockResponseFileReaderAndroidTest("test_devices.json").content,
            conversionType
        )
        assertThat(response).isNotNull()

        runBlocking {
            val actualResponse = apiService.getDevices()

            assertThat(actualResponse).isNotNull()

            assertThat(response.toString())
                .isEqualTo(actualResponse.toString())
        }
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}