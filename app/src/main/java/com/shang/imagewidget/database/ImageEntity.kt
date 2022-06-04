package com.shang.imagewidget.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity(@ColumnInfo(name = "imageUri") val imageUri: Uri?, @PrimaryKey val widgetID: Int) {
}