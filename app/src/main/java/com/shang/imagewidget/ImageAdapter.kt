package com.shang.imagewidget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.shang.imagewidget.database.ImageEntity
import com.shang.imagewidget.databinding.ItemImagePickBinding

class ImageAdapter(private val mListener: ImagePickViewHolder.Listener) :
    ListAdapter<ImageEntity, ImagePickViewHolder>(ImageDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePickViewHolder {
        val binding =
            ItemImagePickBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagePickViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagePickViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, mListener)
        }
    }

}