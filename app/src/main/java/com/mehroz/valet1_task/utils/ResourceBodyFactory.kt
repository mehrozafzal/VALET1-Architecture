package com.mehroz.valet1_task.utils

import co.infinum.retromock.BodyFactory
import kotlin.Throws
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
 class ResourceBodyFactory : BodyFactory {
    @Throws(IOException::class)
    override fun create(input: String): InputStream {
        return FileInputStream(
            ResourceBodyFactory::class.java.classLoader.getResource(input).file
        )
    }
}