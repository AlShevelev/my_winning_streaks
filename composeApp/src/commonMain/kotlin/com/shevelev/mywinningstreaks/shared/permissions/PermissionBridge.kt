package com.shevelev.mywinningstreaks.shared.permissions

class PermissionBridge {
    private var listener: PermissionsBridgeListener? = null

    fun setListener(listener: PermissionsBridgeListener) {
        this.listener = listener
    }

    fun tryRequestPermission(callback: PermissionResultCallback) {
        listener?.tryRequestPermission(callback) ?: error("Callback handler not set")
    }

    fun launchPermissionRequest(callback: PermissionResultCallback) {
        listener?.launchPermissionRequest(callback) ?: error("Callback handler not set")
    }

    fun isPermissionGranted(): Boolean {
        return listener?.isPermissionGranted() ?: false
    }
}
