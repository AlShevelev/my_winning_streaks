package com.shevelev.mywinningstreaks.shared.ui.dialog

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shevelev.mywinningstreaks.screens.main.ui.widgets.dialogs.DialogConstants
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.enter_streak_name
import mywinningstreaks.composeapp.generated.resources.save
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BottomSheetWithTextFieldAndConfirmButton(
    text: String,
    onConfirm: suspend (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var text by remember { mutableStateOf(text) }

    var confirmButtonEnabled by remember { mutableStateOf(text.isNotEmpty()) }

    val dimensions = LocalDimensions.current

    BottomSheetWithConfirmButton(
        onDismiss = onDismiss,
        onConfirm = { onConfirm(text) },
        title = stringResource(Res.string.enter_streak_name),
        confirmButtonText = stringResource(Res.string.save),
        confirmButtonEnabled = confirmButtonEnabled,
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                if (it.length <= DialogConstants.MAX_TITLE_LEN) {
                    text = it
                }
                confirmButtonEnabled = text.isNotEmpty()
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium
                .copy(color = MaterialTheme.colorScheme.onSurface),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .border(
                            width = dimensions.paddingQuarter,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(percent = 30),
                        )
                        .padding(dimensions.paddingSingleAndHalf)
                ) {
                    innerTextField()
                }
            }
        )
    }
}