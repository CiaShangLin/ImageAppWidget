package com.shang.imagewidget.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {

    @Query("select * from ImageEntity")
    fun getImage():List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addImage(imageEntity: ImageEntity)
}