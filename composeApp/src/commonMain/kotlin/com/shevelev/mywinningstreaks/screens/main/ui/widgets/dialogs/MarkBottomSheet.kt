package com.shevelev.mywinningstreaks.screens.main.ui.widgets.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shevelev.mywinningstreaks.coreentities.Status
import com.shevelev.mywinningstreaks.shared.ui.TextWithIcon
import com.shevelev.mywinningstreaks.shared.ui.dialog.BottomSheetWithConfirmButton
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import com.shevelev.mywinningstreaks.shared.ui.theme.color.additional
import com.shevelev.mywinningstreaks.shared.usecases.DiagramUseCase
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.i_done_it
import mywinningstreaks.composeapp.generated.resources.i_sick
import mywinningstreaks.composeapp.generated.resources.ic_face_neutral
import mywinningstreaks.composeapp.generated.resources.ic_face_sad
import mywinningstreaks.composeapp.generated.resources.ic_face_smile
import mywinningstreaks.composeapp.generated.resources.mark_streak
import mywinningstreaks.composeapp.generated.resources.not_today
import mywinningstreaks.composeapp.generated.resources.save
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
internal fun MarkBottomSheet(
    id: Long,
    onDismiss: () -> Unit,
) {
    val useCase = koinInject<DiagramUseCase>()

    val options = listOf(Status.Unknown, Status.Won, Status.Sick, Status.Failed)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(Status.Unknown) }

    var confirmButtonEnabled by remember { mutableStateOf(false) }

    val dimensions = LocalDimensions.current

    BottomSheetWithConfirmButton(
        onDismiss = onDismiss,
        onConfirm = {  },
        title = stringResource(Res.string.mark_streak),
        confirmButtonText = stringResource(Res.string.save),
        confirmButtonEnabled = confirmButtonEnabled,
    ) {
        Column(
            modifier = Modifier.selectableGroup()
        ) {
            for(option in options) {
                if (option == Status.Unknown) {
                    continue
                }

                Row( Modifier.fillMaxWidth()
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = {
                            onOptionSelected(option)
                            confirmButtonEnabled = true
                        }),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = {
                            onOptionSelected(option)
                            confirmButtonEnabled = true
                        }
                    )
                    TextWithIcon(
                        text = when (option) {
                            Status.Won -> stringResource(Res.string.i_done_it)
                            Status.Sick -> stringResource(Res.string.i_sick)
                            Status.Failed -> stringResource(Res.string.not_today)
                            else -> ""
                        },
                        style = MaterialTheme.typography.titleMedium.let {
                            it.copy(fontSize = it.fontSize.times(1.2f))
                        },
                        icon = when (option) {
                            Status.Won -> Res.drawable.ic_face_smile
                            Status.Sick -> Res.drawable.ic_face_neutral
                            Status.Failed -> Res.drawable.ic_face_sad
                            else -> Res.drawable.ic_face_sad
                        },
                    )
                }
            }
        }
    }
}
