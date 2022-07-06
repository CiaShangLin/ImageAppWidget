package com.shang.imagewidget

import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shang.imagewidget.core.MyApplication
import com.shang.imagewidget.database.ImageEntity
import com.shang.imagewidget.ui.ImagePickViewHolder
import com.shang.imagewidget.widget.ImageWidgetProvider
import java.io.ByteArrayOutputStream
import java.io.InputStream


class MainViewModel(application: Application, private val mainRepository: MainRepository) :
    AndroidViewModel(application),
    ImagePickViewHolder.Listener {

    private val _ImageEventLiveData = MutableLiveData<ImageEvent>()
    val imageEventLiveData: LiveData<ImageEvent> = _ImageEventLiveData

    private val _ImageEntityLiveData = MutableLiveData<List<ImageEntity>>()
    val imageEntityLiveData: LiveData<List<ImageEntity>> = _ImageEntityLiveData

    fun refreshWidget() {
        val imageEntityList = mutableListOf<ImageEntity>()
        val ids = AppWidgetManager.getInstance(getApplication())
            .getAppWidgetIds(ComponentName(getApplication(), ImageWidgetProvider::class.java))

        val data = mainRepository.getImage()

        ids?.forEach { id ->
            val bitmap = data?.find {
                it.widgetID == id
            }?.imageBitmap

            imageEntityList.add(ImageEntity(bitmap, id))
        }
        _ImageEntityLiveData.value = imageEntityList

    }

    fun addImage(imageUri: Uri, id: Int) {
        val w = getApplication<MyApplication>().resources.displayMetrics.widthPixels / 2
        compressBitmap(getApplication(), imageUri, w, w)?.let {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray = stream.toByteArray()
            val imageEntity = ImageEntity(byteArray, id)
            mainRepository.addImage(imageEntity)
        }?:Log.w("DEBUG","addImage bitmap is null")
    }

    override fun imagePickClick(id: Int) {
        _ImageEventLiveData.value = ImageEvent.PickImage(id)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
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
}