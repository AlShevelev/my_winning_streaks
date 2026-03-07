package com.shevelev.mywinningstreaks.shared.permissions

interface PermissionsBridgeListener {
    fun allPermissionsGranted(): Boolean

    fun tryRequestPermissions(callback: PermissionResultCallback)

    fun launchPermissionsRequest(callback: PermissionResultCallback)

    fun showSettings()
}