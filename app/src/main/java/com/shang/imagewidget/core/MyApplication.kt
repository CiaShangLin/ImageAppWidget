package com.shang.imagewidget.core

import android.app.Application
import androidx.room.Room
import com.shang.imagewidget.database.AppDatabase
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule,mainViewModel)
        }
    }

}