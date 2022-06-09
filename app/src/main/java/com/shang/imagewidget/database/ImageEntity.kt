package com.shang.imagewidget.database

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity(@ColumnInfo(name = "imageBitmap") val imageBitmap: ByteArray?, @PrimaryKey val widgetID: Int) {
}