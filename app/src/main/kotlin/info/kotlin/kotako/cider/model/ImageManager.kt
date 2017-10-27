package info.kotlin.kotako.cider.model

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ImageManager {

    companion object {
        fun getCurrentStorageDirectory(context: Context): File =
                getStorageDirectory(context, SimpleDateFormat("yyyyMMddHHmmss", Locale.ROOT).format(Date()).toString() + ".png")

        fun getStorageDirectory(context: Context, fileName: String): File =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName)

        fun isExternalStorageWritable(): Boolean =
                Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

        fun saveBitMapAsPng(context: Context, file: File, image: Bitmap): Boolean {
            var success = false
            file.outputStream().use {
                success = image.compress(Bitmap.CompressFormat.PNG, 100, it)
                it.flush()
            }
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    ContentValues().apply {
                        put(MediaStore.Images.Media.TITLE, file.name)
                        put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
                        put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                        put(MediaStore.Images.Media.DATA, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + file.name)
                    })
            return success
        }
    }
}
