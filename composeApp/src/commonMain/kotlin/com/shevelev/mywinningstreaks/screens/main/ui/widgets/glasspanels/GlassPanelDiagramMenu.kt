package com.shevelev.mywinningstreaks.screens.main.ui.widgets.glasspanels

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.ic_delete
import mywinningstreaks.composeapp.generated.resources.ic_edit
import mywinningstreaks.composeapp.generated.resources.ic_mark

@Composable
internal fun ColumnScope.GlassPanelDiagramMenu(
    modifier: Modifier = Modifier,
    markButtonEnabled: Boolean = true,
    onMarkButtonClick: () -> Unit = { },
    onEditButtonClick: () -> Unit = { },
    onDeleteButtonClick: () -> Unit = { },
) {
    GlassPanel(
        modifier = modifier,
    ) {
        GlassPanelButton(
            icon = Res.drawable.ic_mark,
            onClick = onMarkButtonClick,
            enabled = markButtonEnabled,
        )

        GlassPanelDivider()

        GlassPanelButton(
            icon = Res.drawable.ic_edit,
            onClick = onEditButtonClick
        )

        GlassPanelButton(
            icon = Res.drawable.ic_delete,
            onClick = onDeleteButtonClick
        )
    }
}