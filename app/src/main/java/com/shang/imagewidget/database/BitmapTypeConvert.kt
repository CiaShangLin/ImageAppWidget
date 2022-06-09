package com.shang.imagewidget.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream


class BitmapTypeConvert {
    @TypeConverter
    fun stringToBitmap(bast64: String): Bitmap? {
        val byteArray = Base64.decode(bast64,Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }

    @TypeConverter
    fun bitmapToString(bitmap: Bitmap): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream)
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT)
    }
}