package com.enfasys.core

import com.enfasys.core.usecase.Result
import java.io.File

interface FileManager {
    fun upload(path: String, key: String): Result<String>

    fun compressImageAndDeleteTheOldOne(file: File, quality: Int = DEFAULT_QUALITY): File

    fun createNewImage(): File

    fun isFromWeb(uri: String): Boolean {
        return uri.contains("http") || uri.contains("https")
    }

    fun getFileExtension(path: String): String {
        val fileName = File(path).name
        val dotIndex = fileName.lastIndexOf('.')
        return if (dotIndex == -1) {
            ""
        } else {
            fileName.substring(dotIndex + 1)
        }
    }

    companion object {
        const val DEFAULT_QUALITY = 75
    }
}