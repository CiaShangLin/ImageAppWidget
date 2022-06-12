package com.shang.imagewidget

import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shang.imagewidget.core.MyApplication
import com.shang.imagewidget.database.ImageEntity
import com.shang.imagewidget.ui.ImagePickViewHolder
import com.shang.imagewidget.widget.ImageWidgetProvider
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MainViewModel(application: Application,private val mainRepository: MainRepository) : AndroidViewModel(application),
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

    fun addImage(imageUri: Uri,id:Int){
        getApplication<MyApplication>().contentResolver.openInputStream(imageUri)?.let {
            val imageEntity = ImageEntity(getBytes(it),id)
            mainRepository.addImage(imageEntity)
        }
    }

    override fun imagePickClick(id: Int) {
        _ImageEventLiveData.value = ImageEvent.PickImage(id)
    }

    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }
}