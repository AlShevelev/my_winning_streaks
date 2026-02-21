package com.shevelev.mywinningstreaks.shared.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.shevelev.mywinningstreaks.shared.ui.GeneralTextButton
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.ok
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun OkDialog(
    text: String,
    onDismiss: () -> Unit,
    onConfirmation: suspend () -> Unit = {},
) {
    AlertDialog(
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            GeneralTextButton(
                onClick = {
                    onDismiss()
                    onConfirmation()
                },
                text = stringResource(Res.string.ok),
            )
        },
    )
}
