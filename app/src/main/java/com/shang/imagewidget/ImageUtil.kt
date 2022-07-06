package com.shang.imagewidget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

fun compressBitmap(context: Context, imageUri: Uri, width: Int, height: Int): Bitmap? {
    val bitmap = BitmapFactory.Options().run {
        inJustDecodeBounds = true

        BitmapFactory.decodeStream(
            context.contentResolver.openInputStream(imageUri), null, this
        )

        inSampleSize = calculateInSampleSize(this, width, height)

        inJustDecodeBounds = false

        BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri), null, this)
    }
    return bitmap
}

private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}