package com.shevelev.mywinningstreaks.screens.main.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.shevelev.mywinningstreaks.shared.permissions.PermissionBridge
import com.shevelev.mywinningstreaks.shared.permissions.PermissionResultCallback
import com.shevelev.mywinningstreaks.shared.ui.dialog.OkDialog
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.notifications_explanations
import mywinningstreaks.composeapp.generated.resources.notifications_explanations_settings
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun CheckerPermissions() {
    val permissionBridge = remember { getKoin().get<PermissionBridge>() }
    var isPermissionGranted by remember {
        mutableStateOf(permissionBridge.isPermissionGranted())
    }

    var showPermissionExplanationDialog by remember { mutableStateOf(false) }
    var permissionExplanationDialogShown by remember { mutableStateOf(false) }

    var showPermissionExplanationDialogWithSettings by remember { mutableStateOf(false) }

    val permissionCallback = object : PermissionResultCallback {
        override fun onPermissionGranted() {
            isPermissionGranted = true
        }

        override fun onPermissionDenied(isPermanentDenied: Boolean) {
            if (isPermanentDenied) {
                isPermissionGranted = false
                showPermissionExplanationDialogWithSettings = true
            } else {
                if (permissionExplanationDialogShown) {
                    isPermissionGranted = false
                } else {
                    showPermissionExplanationDialog = true
                }
            }
        }
    }

    if (showPermissionExplanationDialog) {
        permissionExplanationDialogShown = true

        OkDialog(
            text = stringResource(Res.string.notifications_explanations),
            onDismiss = {
                showPermissionExplanationDialog = false
                permissionBridge.launchPermissionRequest(permissionCallback)
            },
            onConfirmation = {
                permissionBridge.launchPermissionRequest(permissionCallback)
            }
        )
    }

    if (showPermissionExplanationDialogWithSettings) {
        OkDialog(
            text = stringResource(Res.string.notifications_explanations_settings),
            onDismiss = {
                showPermissionExplanationDialogWithSettings = false
            },
        )
    }

    LaunchedEffect(Unit) {
        if (!isPermissionGranted) {
            permissionBridge.tryRequestPermission(permissionCallback)
        }
    }
}