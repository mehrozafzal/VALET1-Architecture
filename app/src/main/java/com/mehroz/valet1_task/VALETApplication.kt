package com.mehroz.valet1_task

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.mehroz.valet1_task.utils.Constants
import com.mehroz.valet1_task.utils.SharedPrefs
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class VALETApplication : Application() {
    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate() {
        super.onCreate()
        isDarkModeEnabled()
    }

    private fun isDarkModeEnabled() {
        if(sharedPrefs.getValue(Constants.MODE_KEY,false) as Boolean)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}