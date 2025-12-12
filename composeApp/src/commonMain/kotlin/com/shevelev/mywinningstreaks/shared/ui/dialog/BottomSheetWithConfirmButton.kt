package com.shevelev.mywinningstreaks.shared.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import com.shevelev.mywinningstreaks.shared.ui.GeneralTextButton
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheetWithConfirmButton(
    onDismiss: () -> Unit,
    onConfirm: suspend () -> Unit,
    title: String,
    confirmButtonText: String,
    confirmButtonEnabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    val dimensions = LocalDimensions.current

    val sheetState = rememberModalBottomSheetState()

    val scope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.padding(horizontal = dimensions.paddingDouble),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensions.paddingDouble)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge
            )

            content()

            GeneralTextButton(
                enabled = confirmButtonEnabled,
                onClick = {
                    keyboardController?.hide()
                    scope.launch {
                        onConfirm()
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
                },
                text = confirmButtonText,
            )
        }
    }
}