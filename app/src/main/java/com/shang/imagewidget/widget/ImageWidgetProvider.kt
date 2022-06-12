package com.shang.imagewidget.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import com.shang.imagewidget.KoinInstance
import com.shang.imagewidget.R
import com.shang.imagewidget.database.ImageEntity

class ImageWidgetProvider : AppWidgetProvider() {

    private var data: List<ImageEntity>? = null

    init {
        data = KoinInstance.appDatabase.getImageDao().getImage()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.d("DEBUG", "onReceive")

        AppWidgetManager.getInstance(context)
                .getAppWidgetIds(ComponentName(context!!, ImageWidgetProvider::class.java)).forEach { id->
            var bitmap =  data?.find {
                it.widgetID == id
            }?.imageBitmap
            if(bitmap == null){
                val views = RemoteViews(context.packageName, R.layout.image_widget)
                views.setImageViewResource(R.id.imageView, R.drawable.img_preview)
                AppWidgetManager.getInstance(context).updateAppWidget(id, views)
            }else{
                val views = RemoteViews(context.packageName, R.layout.image_widget)
                views.setImageViewBitmap(R.id.imageView,BitmapFactory.decodeByteArray(bitmap,0,bitmap?.size?:0))
                AppWidgetManager.getInstance(context).updateAppWidget(id, views)
            }
        }
//        if (id != -1) {
//            val uri = Uri.parse(intent?.getStringExtra("uri"))
//            val bitmap =  BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(uri))
//            val views = RemoteViews(context?.packageName, R.layout.image_widget)
//            views.setImageViewBitmap(R.id.imageView, bitmap)
//            AppWidgetManager.getInstance(context).updateAppWidget(id, views)
//        } else
//        {
//            val ids = AppWidgetManager.getInstance(context)
//                .getAppWidgetIds(ComponentName(context!!, ImageWidgetProvider::class.java))
//            val views = RemoteViews(context?.packageName, R.layout.image_widget)
//            views.setImageViewResource(R.id.imageView, R.drawable.img_preview)
//            AppWidgetManager.getInstance(context).updateAppWidget(ids, views)
//        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        Log.d("DEBUG", "onUpdate")
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        Log.d("DEBUG", "onAppWidgetOptionsChanged")
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d("DEBUG", "onDeleted")
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d("DEBUG", "onEnabled")
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.d("DEBUG", "onDisabled")
    }

    override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
        Log.d("DEBUG", "onRestored")
    }
}
