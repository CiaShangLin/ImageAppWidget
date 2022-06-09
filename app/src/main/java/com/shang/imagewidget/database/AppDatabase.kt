package com.shang.imagewidget.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ImageEntity::class], version = 1)
@TypeConverters(BitmapTypeConvert::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getImageDao(): ImageDao
}