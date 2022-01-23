package com.mehroz.valet1_task.presentation

import androidx.test.core.app.launchActivity
import androidx.test.filters.MediumTest
import com.mehroz.valet1_task.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@MediumTest
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldOpenMainActivity() {
        val mainActivityScenario = launchActivity<MainActivity>()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldOpenHomeFragment() {
        val mainActivityScenario = launchFragmentInHiltContainer<HomeFragment> {  }
    }
}
