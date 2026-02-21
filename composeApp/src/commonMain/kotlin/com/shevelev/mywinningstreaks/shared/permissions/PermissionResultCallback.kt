package com.shevelev.mywinningstreaks.shared.permissions

interface PermissionResultCallback {
    fun onPermissionGranted()
    fun onPermissionDenied(isPermanentDenied: Boolean)
}