package com.shang.imagewidget.core

import com.shang.imagewidget.ui.GridMode
import com.tencent.mmkv.MMKV

object MMKVUtil {

    private const val MODE = "MODE"
    private val _mmkv = MMKV.defaultMMKV()


    fun setMode(mode: GridMode) {
        _mmkv.putInt(MODE, mode.mode)
    }

    fun getMode(): GridMode {
        val mode = _mmkv.getInt(MODE, GridMode.TWO.mode)
        return when(mode){
            GridMode.TWO.mode-> GridMode.TWO
            GridMode.THREE.mode-> GridMode.THREE
            else->throw RuntimeException("Not found this mode")
        }
    }
}