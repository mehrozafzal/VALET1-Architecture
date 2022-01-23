package com.mehroz.valet1_task.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SharedPrefs constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {

    fun putValue(key: String, value: Any? = null) {
        val editor = sharedPreferences.edit()
        when (value) {
            is String -> {
                editor.putString(key, value)
            }
            is Int -> {
                editor.putInt(key, value)
            }

            is Float -> {
                editor.putFloat(key, value)
            }

            is Double -> {
                editor.putFloat(key, value.toFloat())
            }

            is Long -> {
                editor.putLong(key, value)
            }

            is Boolean -> {
                editor.putBoolean(key, value)
            }

            else -> {
                editor.putString(key, gson.toJson(value))
            }
        }
        editor.apply()
    }

    /**
     * Function to get value for primitive types
     * @param key - Define the key for the value you want to access
     * @param defaultValue - Define the default value while accessing the key-value pair
     */
    fun getValue(key: String, defaultValue: Any?): Any? {
        return when (defaultValue) {
            is String -> {
                sharedPreferences.getString(key, defaultValue)
            }
            is Int -> {
                sharedPreferences.getInt(key, defaultValue)
            }

            is Float -> {
                sharedPreferences.getFloat(key, defaultValue)
            }

            is Double -> {
                sharedPreferences.getFloat(key, defaultValue.toFloat()).toDouble()
            }

            is Long -> {
                sharedPreferences.getLong(key, defaultValue)
            }

            is Boolean -> {
                sharedPreferences.getBoolean(key, defaultValue)
            }
            else -> {
                null
            }
        }
    }

    /**
     * Function to get value for primitive types
     * @param key - Define the key for the value you want to access
     * @param defaultValue - Define the default value while accessing the key-value pair
     * @param classType - Define the type of class that you want to access against a specific key e.g AccessTokenWrapper::class.java
     */
    fun getValue(key: String, defaultValue: String?, classType: Any): Any? {
        return when (classType) {
            is List<*> -> {
                val typeOfT = TypeToken.getParameterized(List::class.java, classType as Type?).type
                gson.fromJson<List<*>>(sharedPreferences.getString(key, defaultValue), typeOfT)
            }
            is Array<*> -> {
                gson.fromJson<Array<*>>(
                    sharedPreferences.getString(key, null),
                    classType as Type?
                )
            }
            else -> {
                gson.fromJson(sharedPreferences.getString(key, defaultValue), classType as Type?)
            }
        }
    }

    fun removeKey(key: String) {
        val editor = sharedPreferences.edit()
        if (editor != null) {
            editor.remove(key)
            editor.apply()
        }
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        const val PREFS_NAME = "1ValetSharedPreferences"
    }
}