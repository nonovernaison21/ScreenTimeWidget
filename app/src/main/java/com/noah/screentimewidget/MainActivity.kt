package com.noah.screentimewidget

import android.app.AppOpsManager
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusText = findViewById<TextView>(R.id.statusText)
        val permissionButton = findViewById<Button>(R.id.permissionButton)
        val refreshButton = findViewById<Button>(R.id.refreshButton)

        updateStatus(statusText)

        permissionButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }

        refreshButton.setOnClickListener {
            forceWidgetRefresh()
            updateStatus(statusText)
        }
    }

    override fun onResume() {
        super.onResume()
        findViewById<TextView>(R.id.statusText)?.let { updateStatus(it) }
    }

    private fun updateStatus(statusText: TextView) {
        statusText.text = if (hasUsageAccess()) {
            "Permission accordée ✓\nLe widget peut afficher ton temps d'écran."
        } else {
            "Permission requise.\nAppuie sur le bouton ci-dessous, puis active\n\"ScreenTimeWidget\" dans la liste."
        }
    }

    private fun hasUsageAccess(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName)
        } else {
            @Suppress("DEPRECATION")
            appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName)
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun forceWidgetRefresh() {
        val intent = Intent(this, ScreenTimeWidgetProvider::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            val ids = AppWidgetManager.getInstance(application)
                .getAppWidgetIds(ComponentName(application, ScreenTimeWidgetProvider::class.java))
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        }
        sendBroadcast(intent)
    }
}
