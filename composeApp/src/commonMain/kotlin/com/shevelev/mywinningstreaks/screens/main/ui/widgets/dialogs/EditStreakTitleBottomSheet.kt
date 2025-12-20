package com.shevelev.mywinningstreaks.screens.main.ui.widgets.dialogs

import androidx.compose.runtime.Composable
import com.shevelev.mywinningstreaks.shared.ui.dialog.BottomSheetWithTextFieldAndConfirmButton
import com.shevelev.mywinningstreaks.shared.usecases.DiagramUseCase
import org.koin.compose.koinInject

@Composable
internal fun EditStreakTitleBottomSheet(
    title: String,
    id: Long,
    onDismiss: () -> Unit,
) {
    val useCase = koinInject<DiagramUseCase>()

    BottomSheetWithTextFieldAndConfirmButton(
        text = title,
        onConfirm = { text -> useCase.updateStreakTitle(id, text)},
        onDismiss = onDismiss,
    )
}