package com.shevelev.mywinningstreaks

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.shevelev.mywinningstreaks.shared.permissions.PermissionBridge
import com.shevelev.mywinningstreaks.shared.permissions.PermissionResultCallback
import com.shevelev.mywinningstreaks.shared.permissions.PermissionsBridgeListener
import org.koin.core.context.GlobalContext

class MainActivity : ComponentActivity(), PermissionsBridgeListener {
    private var permissionResultCallback: PermissionResultCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        GlobalContext.get().get<PermissionBridge>().setListener(this)

        setContent {
            App()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun tryRequestPermission(callback: PermissionResultCallback) {
        when {
            isPermissionGranted() -> callback.onPermissionGranted()

            shouldShowRequestPermissionRationale(PERMISSION) -> {
                callback.onPermissionDenied(false)
            }

            else -> launchPermissionRequest(callback)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun launchPermissionRequest(callback: PermissionResultCallback) {
        permissionResultCallback = callback
        requestPermissionLauncher.launch(PERMISSION)
    }

    override fun isPermissionGranted() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(this, PERMISSION) ==
                PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                permissionResultCallback?.onPermissionGranted()
            } else {
                val denied = !shouldShowRequestPermissionRationale(PERMISSION)
                permissionResultCallback?.onPermissionDenied(denied)
            }
        }

    companion object {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private const val PERMISSION = Manifest.permission.POST_NOTIFICATIONS
    }
}
