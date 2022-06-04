package com.shang.imagewidget

sealed class ImageEvent {
    class PickImage(val id: Int) : ImageEvent()
}