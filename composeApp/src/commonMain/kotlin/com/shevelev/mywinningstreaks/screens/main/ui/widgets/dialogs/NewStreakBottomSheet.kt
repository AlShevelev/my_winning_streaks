package com.shevelev.mywinningstreaks.screens.main.ui.widgets.dialogs

import androidx.compose.runtime.Composable
import com.shevelev.mywinningstreaks.shared.ui.dialog.BottomSheetWithTextFieldAndConfirmButton
import com.shevelev.mywinningstreaks.shared.usecases.DiagramUseCase
import org.koin.compose.koinInject

@Composable
internal fun NewStreakBottomSheet(
    onDismiss: () -> Unit,
) {
    val useCase = koinInject<DiagramUseCase>()

    BottomSheetWithTextFieldAndConfirmButton(
        text = "",
        onConfirm = { text -> useCase.addStreak(text)},
        onDismiss = onDismiss,
    )
}