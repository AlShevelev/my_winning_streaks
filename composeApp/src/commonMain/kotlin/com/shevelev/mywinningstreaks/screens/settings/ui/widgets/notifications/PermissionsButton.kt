package com.shevelev.mywinningstreaks.screens.settings.ui.widgets.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.shevelev.mywinningstreaks.shared.permissions.PermissionBridge
import com.shevelev.mywinningstreaks.shared.permissions.PermissionResultCallback
import com.shevelev.mywinningstreaks.shared.ui.GeneralTextButton
import com.shevelev.mywinningstreaks.shared.ui.dialog.OkDialog
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.notifications_explanations
import mywinningstreaks.composeapp.generated.resources.notifications_explanations_settings
import mywinningstreaks.composeapp.generated.resources.request_permission_notifications
import org.jetbrains.compose.resources.stringResource
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun PermissionsButton(
    modifier: Modifier = Modifier,
    permissionGranted: () -> Unit,
) {
    val permissionBridge = remember { getKoin().get<PermissionBridge>() }

    var showPermissionExplanationDialog by remember { mutableStateOf(false) }
    var permissionExplanationDialogShown by remember { mutableStateOf(false) }

    var showPermissionExplanationDialogWithSettings by remember { mutableStateOf(false) }

    val permissionCallback = object : PermissionResultCallback {
        override fun onPermissionGranted() {
            permissionGranted()
        }

        override fun onPermissionDenied(isPermanentDenied: Boolean) {
            if (isPermanentDenied) {
                showPermissionExplanationDialogWithSettings = true
            } else {
                if (!permissionExplanationDialogShown) {
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

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        GeneralTextButton(
            onClick = {
                if (permissionBridge.isPermissionGranted()) {
                    permissionGranted()
                } else {
                    permissionBridge.tryRequestPermission(permissionCallback)
                }
            },
            text = stringResource(Res.string.request_permission_notifications)
        )
    }
}