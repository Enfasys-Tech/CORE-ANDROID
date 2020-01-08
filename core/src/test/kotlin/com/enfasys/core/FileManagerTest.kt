package com.enfasys.core

import com.enfasys.core.usecase.Result
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FileManagerTest {
    private val fakeFileManager = object : FileManager {
        override fun upload(path: String, key: String) = Result.success("empty")

        override fun compressImageAndDeleteTheOldOne(file: File, quality: Int) = File("empty")

        override fun createNewImage() = File("empty")
    }

    @Test
    fun `Returns true when uri is from web`() {
        val httpUri = "http://www.images.com/123.jpg"
        val httpsUri = "https://www.images.com/123.jpg"

        assertTrue(fakeFileManager.isFromWeb(httpUri))
        assertTrue(fakeFileManager.isFromWeb(httpsUri))
    }

    @Test
    fun `Returns false when uri is from local device`() {
        val localUri = "file:///storage/emulated/0/video.mp4"

        assertFalse(fakeFileManager.isFromWeb(localUri))
    }

    @Test
    fun `Returns file extension from file`() {
        val path = "file:///storage/emulated/0/photo.jpg"

        assertEquals("jpg", fakeFileManager.getFileExtension(path))
    }

    @Test
    fun `Returns empty string when there is no file extension`() {
        val path = "file:///storage/emulated/0/photo"

        assertEquals("", fakeFileManager.getFileExtension(path))
    }
}