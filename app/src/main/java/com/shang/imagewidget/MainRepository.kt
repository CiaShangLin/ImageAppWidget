package com.shang.imagewidget

import com.shang.imagewidget.database.ImageDao
import com.shang.imagewidget.database.ImageEntity

class MainRepository(private val imageDao: ImageDao) {

    fun getImage(): List<ImageEntity> {
        return imageDao.getImage()
    }

    fun addImage(imageEntity:ImageEntity){
        imageDao.addImage(imageEntity)
    }
}