package com.shang.imagewidget

import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shang.imagewidget.database.ImageEntity

class MainViewModel(application: Application) : AndroidViewModel(application),
    ImagePickViewHolder.Listener {

    private val _ImageEventLiveData = MutableLiveData<ImageEvent>()
    val imageEventLiveData: LiveData<ImageEvent> = _ImageEventLiveData

    private val _ImageEntityLiveData = MutableLiveData<List<ImageEntity>>()
    val imageEntityLiveData: LiveData<List<ImageEntity>> = _ImageEntityLiveData

    fun refreshWidget() {
        val imageEntityList = mutableListOf<ImageEntity>()
        val ids = AppWidgetManager.getInstance(getApplication())
            .getAppWidgetIds(ComponentName(getApplication(), ImageWidgetProvider::class.java))

        val data = MyApplication.getDataBase()?.getImageDao()?.getImage()

        ids?.forEach { id ->
            val uri = data?.find {
                it.widgetID == id
            }?.imageUri

            imageEntityList.add(ImageEntity(uri, id))
        }
        _ImageEntityLiveData.value = imageEntityList
    }

    fun addImage(imageUri: Uri,id:Int){
        val imageEntity = ImageEntity(imageUri,id)
        MyApplication.getDataBase()?.getImageDao()?.addImage(imageEntity)
    }

    override fun imagePickClick(id: Int) {
        _ImageEventLiveData.value = ImageEvent.PickImage(id)
    }
}