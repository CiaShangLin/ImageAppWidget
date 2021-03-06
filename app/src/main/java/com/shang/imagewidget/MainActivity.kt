package com.shang.imagewidget

import android.Manifest
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.shang.imagewidget.core.MMKVUtil
import com.shang.imagewidget.databinding.ActivityMainBinding
import com.shang.imagewidget.ui.GridMode
import com.shang.imagewidget.ui.ImageAdapter
import com.shang.imagewidget.widget.ImageWidgetProvider
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    companion object {
        private val _PickImageRequestCode = 200
        private val _PermissionRequestCode = 100
        private val _Permissions = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private val _viewModel by viewModel<MainViewModel>()
    private lateinit var _gridMode: GridMode
    private lateinit var _binding: ActivityMainBinding
    private val _imageAdapter by lazy { ImageAdapter(_viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        initRvImage()


        _viewModel.imageEntityLiveData.observe(this) {
            _imageAdapter.submitList(it)
            it.forEach {
                updateImageWidget(it.imageBitmap, it.widgetID)
            }
        }

        _viewModel.imageEventLiveData.observe(this) {
            when (it) {
                is ImageEvent.PickImage -> {
                    if (checkPermissions()) {
                        pickImage()
                    }
                }
            }
        }
    }

    private fun initRvImage() {
        _gridMode = MMKVUtil.getMode()
        when (_gridMode) {
            GridMode.TWO -> {
                _binding.rvImage.layoutManager = GridLayoutManager(this, 2)
            }
            GridMode.THREE -> {
                _binding.rvImage.layoutManager = GridLayoutManager(this, 3)
            }
        }
        _binding.rvImage.adapter = _imageAdapter
    }

    private fun pickImage() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select image"), _PickImageRequestCode)
    }

    override fun onResume() {
        super.onResume()
        _viewModel.refreshWidget()
    }

    private fun checkPermissions(): Boolean {
        var guard = true
        _Permissions.forEach {
            guard = guard && ContextCompat.checkSelfPermission(
                this@MainActivity,
                it
            ) == PERMISSION_GRANTED
        }
        if (guard) {
            return true
        } else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                _Permissions,
                _PermissionRequestCode
            )
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        menu.get(0).icon = when (_gridMode) {
            GridMode.TWO -> ContextCompat.getDrawable(this, R.drawable.icon_two_grid)
            GridMode.THREE -> ContextCompat.getDrawable(this, R.drawable.icon_three_grid)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeMode -> {
                when (_gridMode) {
                    GridMode.TWO -> {
                        item.icon = ContextCompat.getDrawable(this, R.drawable.icon_three_grid)
                        MMKVUtil.setMode(GridMode.THREE)
                        _gridMode = GridMode.THREE
                        _binding.rvImage.layoutManager = GridLayoutManager(this, 3)
                    }
                    GridMode.THREE -> {
                        item.icon = ContextCompat.getDrawable(this, R.drawable.icon_two_grid)
                        MMKVUtil.setMode(GridMode.TWO)
                        _gridMode = GridMode.TWO
                        _binding.rvImage.layoutManager = GridLayoutManager(this, 2)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun addWidget() {
//        val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(this)
//        val myProvider = ComponentName(this, ImageWidgetProvider::class.java)
//
//        val successCallback: PendingIntent? = if (appWidgetManager.isRequestPinAppWidgetSupported) {
//            Intent().let { intent ->
//                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            }
//        } else {
//            null
//        }
//
//        successCallback?.also { pendingIntent ->
//            appWidgetManager.requestPinAppWidget(myProvider, null, pendingIntent)
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == _PermissionRequestCode) {
            var guard = true
            grantResults.forEach {
                guard = guard && (it == PERMISSION_GRANTED)
            }
            if (guard) {
                pickImage()
            } else {
                Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            _PickImageRequestCode -> {
                val id = (_viewModel.imageEventLiveData.value as ImageEvent.PickImage).id
                val uri = data?.data
                uri?.let {
                    updateImageWidget(it, id)
                    _viewModel.addImage(it, id)
                    _viewModel.refreshWidget()
                }
            }
        }
    }

    private fun updateImageWidget(imageUri: Uri, id: Int) {
        //????????????????????????widget????????????????????????
        val w = resources.displayMetrics.widthPixels / 2
        val bitmap = compressBitmap(this, imageUri, w, w)
        val views = RemoteViews(packageName, R.layout.image_widget)
        views.setImageViewBitmap(R.id.imageView, bitmap)
        AppWidgetManager.getInstance(this).updateAppWidget(id, views)
    }

    private fun updateImageWidget(byteArray: ByteArray?, id: Int) {
        if (byteArray == null) {
            return
        }
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray?.size ?: 0)
        val views = RemoteViews(packageName, R.layout.image_widget)
        views.setImageViewBitmap(R.id.imageView, bitmap)
        AppWidgetManager.getInstance(this).updateAppWidget(id, views)
    }
}