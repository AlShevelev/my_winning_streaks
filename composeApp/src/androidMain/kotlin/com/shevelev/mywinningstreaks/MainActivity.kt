package com.shevelev.mywinningstreaks

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.shevelev.mywinningstreaks.shared.permissions.PermissionBridge
import com.shevelev.mywinningstreaks.shared.permissions.PermissionResultCallback
import com.shevelev.mywinningstreaks.shared.permissions.PermissionsBridgeListener
import org.koin.core.context.GlobalContext


class MainActivity : ComponentActivity(), PermissionsBridgeListener {
    private var permissionResultCallback: PermissionResultCallback? = null

    private val permissionsToCheck
        @SuppressLint("InlinedApi")
        get() = listOf(POST_NOTIFICATIONS, SCHEDULE_EXACT_ALARM)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        GlobalContext.get().get<PermissionBridge>().setListener(this)

        setContent {
            App()
        }
    }

    override fun allPermissionsGranted(): Boolean =
        permissionsToCheck.all { isPermissionGranted(it) }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun tryRequestPermissions(callback: PermissionResultCallback) {
        val permissionsToCheck = permissionsToCheck

        val notGranted = permissionsToCheck.filterNot { isPermissionGranted(it) }
        if (notGranted.isEmpty()) {
            callback.onAllPermissionGranted()
            return
        }

        if (notGranted.any { shouldShowRequestPermissionRationale(it) }) {
            callback.shouldShowRationale()
            return
        }

        launchPermissionsRequest(callback)
    }

    override fun launchPermissionsRequest(callback: PermissionResultCallback) {
        permissionResultCallback = callback
        requestPermissionLauncher.launch(permissionsToCheck.toTypedArray())
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun showSettings() {
        val intent = Intent().apply {
            setAction(ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            setData(("package:$packageName").toUri())
        }
        startActivity(intent)
    }

    private fun isPermissionGranted(permissions: String): Boolean {
        return when (permissions) {
            POST_NOTIFICATIONS -> ContextCompat.checkSelfPermission(
                this,
                permissions
            ) == PackageManager.PERMISSION_GRANTED

            SCHEDULE_EXACT_ALARM -> {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    true
                } else {
                    val alarmManager = getSystemService(ALARM_SERVICE) as android.app.AlarmManager
                    alarmManager.canScheduleExactAlarms()
                }
            }

            else -> false
        }
    }

    @SuppressLint("InlinedApi")
    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val notGranted = result.entries.filterNot { it.value }.map { it.key }

            if (notGranted.isEmpty()) {
                permissionResultCallback?.onAllPermissionGranted()
                return@registerForActivityResult
            }

            if (notGranted.any { shouldShowRequestPermissionRationale(it) }) {
                permissionResultCallback?.shouldShowRationale()
            } else {
                permissionResultCallback?.onPermissionDeniedPermanent(
                    openSettings = notGranted.contains(SCHEDULE_EXACT_ALARM) && notGranted.size == 1
                )
            }
        }

    companion object {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private const val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS
        @RequiresApi(Build.VERSION_CODES.S)
        private const val SCHEDULE_EXACT_ALARM = Manifest.permission.SCHEDULE_EXACT_ALARM
    }
}
