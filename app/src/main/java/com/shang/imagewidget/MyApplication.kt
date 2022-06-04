package com.shang.imagewidget

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shang.imagewidget.database.AppDatabase
import com.tencent.mmkv.MMKV

class MyApplication : Application() {

    companion object {
        private var instance: AppDatabase? = null
        fun getDataBase(): AppDatabase? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        initAppDatabase()
    }

    private fun initAppDatabase() {
        if (instance == null) {
            instance = Room.databaseBuilder(this, AppDatabase::class.java, "AppDataBase.db")
                .allowMainThreadQueries()
                .build()
        }
    }
}