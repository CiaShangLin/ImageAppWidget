package com.shang.imagewidget.ui

import androidx.recyclerview.widget.DiffUtil
import com.shang.imagewidget.database.ImageEntity

object ImageDiffUtil : DiffUtil.ItemCallback<ImageEntity>() {
    override fun areItemsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
        return oldItem.widgetID == newItem.widgetID
    }
}