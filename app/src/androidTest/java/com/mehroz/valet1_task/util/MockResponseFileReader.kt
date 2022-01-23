package com.mehroz.valet1_task.util

import java.io.InputStreamReader

class MockResponseFileReaderAndroidTest(path: String) {

    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}