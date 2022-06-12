package com.shang.imagewidget

import com.shang.imagewidget.database.AppDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DataBase:KoinComponent {
    val database:AppDatabase by inject()
}