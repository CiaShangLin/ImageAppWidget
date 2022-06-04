package com.shang.imagewidget

import androidx.recyclerview.widget.RecyclerView
import com.shang.imagewidget.database.ImageEntity
import com.shang.imagewidget.databinding.ItemImagePickBinding

class ImagePickViewHolder(private val _binding: ItemImagePickBinding) :
    RecyclerView.ViewHolder(_binding.root) {

    interface Listener{
        fun imagePickClick(id:Int)
    }

    private var mData: ImageEntity? = null
    private var mListener: Listener? = null

    init {
        itemView.setOnClickListener { view ->
            mData?.let {
                mListener?.imagePickClick(it.widgetID)
            }
        }
    }

    fun bind(imageEntity: ImageEntity, listener: Listener) {
        mData = imageEntity
        mListener = listener
        if (imageEntity.imageUri == null) {
            _binding.ivImagePick.setImageResource(R.drawable.icon_add)
        } else {
            _binding.ivImagePick.setImageURI(imageEntity.imageUri)
        }
    }
}