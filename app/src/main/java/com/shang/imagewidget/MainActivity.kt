package com.shang.imagewidget

import android.Manifest
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.security.Permissions

class MainActivity : AppCompatActivity() {

    companion object {
        val _requestCode = 100
        val permissions = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val imageWidgetProvider = ImageWidgetProvider()
//        imageWidgetProvider.onUpdate(this, AppWidgetManager.getInstance(this),0)

        findViewById<Button>(R.id.btImagePick).apply {
            this.setOnClickListener {
                var guard = true
                permissions.forEach {
                    guard = guard && ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        it
                    ) == PERMISSION_GRANTED
                }
                if (guard) {

                } else {
                    ActivityCompat.requestPermissions(this@MainActivity, permissions, _requestCode)
                }
            }
        }
    }
}