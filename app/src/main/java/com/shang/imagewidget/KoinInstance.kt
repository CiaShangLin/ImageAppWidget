package com.shang.imagewidget

import com.shang.imagewidget.database.AppDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object KoinInstance : KoinComponent {
    val appDatabase: AppDatabase by inject()
}