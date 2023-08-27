package com.example.decise.file

import android.content.Context
import android.content.CursorLoader
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class FileUploader(private val context: Context) {

    fun getMultipartBodyPart(fileName: String, fileUri: Uri?): MultipartBody.Part? {
        if (fileUri != null) {
            val file = File(getRealPathFromUri(fileUri)!!)
            val requestFile =
                file.asRequestBody(context.contentResolver.getType(fileUri)!!.toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(fileName, file.name, requestFile)
        } else
            return null
    }

    fun getMultipartBodyPartFromFile(fileName: String, file: File?): MultipartBody.Part? {
        return if (file != null) {
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(fileName, file.name, requestFile)
        } else {
            null
        }

    }
    fun getMultipartBodyPartFromBitmap(bitmap: Bitmap): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream) // You can adjust compression quality
        val byteArray = stream.toByteArray()

        val requestFile = byteArray.toRequestBody("image/jpeg".toMediaType())
        return MultipartBody.Part.createFormData("file", "image.jpg", requestFile)
    }
    fun getRealPathFromUri(fileUri: Uri): String? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(context, fileUri, filePathColumn, null, null, null)
        val cursor = loader.loadInBackground()
        return if (cursor == null) {
            fileUri.path
        } else {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            picturePath
        }
    }

    fun createPartFromString(text: String): RequestBody {
        return text.toRequestBody(MultipartBody.FORM)
    }
}
