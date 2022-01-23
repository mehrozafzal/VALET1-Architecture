package com.mehroz.valet1_task.data.local

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mehroz.valet1_task.utils.Constants.MODE_KEY
import com.mehroz.valet1_task.utils.SharedPrefs
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
class PreferenceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testWhenDarkModeIsOn(){
        sharedPrefs.putValue(MODE_KEY,true)
        val mode = sharedPrefs.getValue(MODE_KEY,false) as Boolean
        assertThat(mode).isTrue()
    }

    @Test
    fun testWhenDarkModeIsOff(){
        sharedPrefs.putValue(MODE_KEY,false)
        val mode = sharedPrefs.getValue(MODE_KEY,false) as Boolean
        assertThat(mode).isFalse()
    }
}