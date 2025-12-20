package com.shevelev.mywinningstreaks.screens.main.ui.widgets.dialogs

import androidx.compose.runtime.Composable
import com.shevelev.mywinningstreaks.shared.ui.dialog.ConfirmationDialog
import com.shevelev.mywinningstreaks.shared.usecases.DiagramUseCase
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.delete_request
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
internal fun StreakDeleteConfirmationDialog(
    id: Long,
    onDismiss: () -> Unit,
) {
    val useCase = koinInject<DiagramUseCase>()

    ConfirmationDialog(
        text = stringResource(Res.string.delete_request),
        onDismiss = onDismiss,
        onConfirmation = {
            useCase.deleteStreak(id)
            onDismiss()
        }
    )
}