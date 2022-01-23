package com.mehroz.valet1_task.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mehroz.valet1_task.data.local.AppDatabase
import com.mehroz.valet1_task.utils.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideDeviceDao(db: AppDatabase) = db.deviceDao()

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideSharedPreference(application: Application): SharedPreferences {
        return application.getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE)
    }


    @Singleton
    @Provides
    fun provideSharedPrefs(sharedPreferences: SharedPreferences, gson: Gson): SharedPrefs {
        return SharedPrefs(sharedPreferences, gson)
    }
}