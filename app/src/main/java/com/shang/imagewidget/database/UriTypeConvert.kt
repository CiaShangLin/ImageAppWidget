package com.shang.imagewidget.database

import android.net.Uri
import androidx.room.TypeConverter

class UriTypeConvert {

    @TypeConverter
    fun stringToUri(uri: String): Uri? {
        return Uri.parse(uri)
    }

    @TypeConverter
    fun uriToString(uri:Uri?): String? {
        return uri?.toString()
    }
}