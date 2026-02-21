package com.shevelev.mywinningstreaks.shared.permissions

interface PermissionsBridgeListener {
    fun isPermissionGranted(): Boolean

    fun tryRequestPermission(callback: PermissionResultCallback)

    fun launchPermissionRequest(callback: PermissionResultCallback)
}