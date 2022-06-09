package com.shang.imagewidget.core

import androidx.room.Room
import com.shang.imagewidget.MainRepository
import com.shang.imagewidget.MainViewModel
import com.shang.imagewidget.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "AppDataBase.db")
            .allowMainThreadQueries()
            .build()
    }

    single {
        get<AppDatabase>().getImageDao()
    }
}

val mainViewModel = module {
    viewModel {
        MainViewModel(androidApplication(),get())
    }

    single {
        MainRepository(get())
    }
}