package com.shevelev.mywinningstreaks.shared.permissions

interface PermissionResultCallback {
    fun onAllPermissionGranted()
    fun shouldShowRationale()
    fun onPermissionDeniedPermanent(openSettings: Boolean)
}