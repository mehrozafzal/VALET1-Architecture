package com.mehroz.valet1_task.di

import android.app.Application
import co.infinum.retromock.Retromock
import com.mehroz.valet1_task.BuildConfig
import com.mehroz.valet1_task.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetroMock(retrofit: Retrofit, context: Application): Retromock {
        return Retromock.Builder()
            .retrofit(retrofit)
            .defaultBehavior { 0 }
            .defaultBodyFactory(context.assets::open)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retroMock: Retromock): ApiService =
        retroMock.create(ApiService::class.java)
}