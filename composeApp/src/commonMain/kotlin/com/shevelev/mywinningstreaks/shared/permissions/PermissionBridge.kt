package com.shevelev.mywinningstreaks.shared.permissions

class PermissionBridge {
    private var listener: PermissionsBridgeListener? = null

    fun setListener(listener: PermissionsBridgeListener) {
        this.listener = listener
    }

    fun tryRequestPermission(callback: PermissionResultCallback) {
        listener?.tryRequestPermissions(callback) ?: error("Callback handler not set")
    }

    fun launchPermissionRequest(callback: PermissionResultCallback) {
        listener?.launchPermissionsRequest(callback) ?: error("Callback handler not set")
    }

    fun isPermissionGranted(): Boolean {
        return listener?.allPermissionsGranted() ?: false
    }

    fun showSettings() {
        listener?.showSettings() ?: error("Callback handler not set")
    }
}
