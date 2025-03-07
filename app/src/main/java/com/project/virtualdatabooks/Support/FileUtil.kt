package com.project.virtualdatabooks.Support

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

object FileUtil {
    fun uriToFile(uri: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val tempFile = File(context.cacheDir, "imported_file.xlsx")

        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val outputStream: OutputStream = FileOutputStream(tempFile)

        inputStream?.use { input ->
            outputStream.use { output ->
                val inputChannel = (input as FileInputStream).channel
                val outputChannel = (output as FileOutputStream).channel
                inputChannel.transferTo(0, inputChannel.size(), outputChannel)
            }
        }
        return tempFile
    }
}
