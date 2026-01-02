package com.shevelev.mywinningstreaks.screens.main.ui.widgets.glasspanels

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.ic_add
import mywinningstreaks.composeapp.generated.resources.ic_circle
import mywinningstreaks.composeapp.generated.resources.ic_four_circles
import mywinningstreaks.composeapp.generated.resources.ic_settings

@Composable
internal fun ColumnScope.GlassPanelMainMenu(
    modifier: Modifier = Modifier,
    addButtonEnabled: Boolean = true,
    pagerButtonEnabled: Boolean = true,
    gridButtonEnabled: Boolean = true,
    settingsButtonEnabled: Boolean = true,
    onAddButtonClick: () -> Unit = { },
    onPagerButtonClick: () -> Unit = { },
    onGridButtonClick: () -> Unit = { },
    onSettingsButtonClick:  () -> Unit = { },
) {
    GlassPanel(
        modifier = modifier,
    ) {
        GlassPanelButton(
            icon = Res.drawable.ic_add,
            internalPadding = 0.dp,
            onClick = onAddButtonClick,
            enabled = addButtonEnabled,
        )

        GlassPanelDivider()

        GlassPanelButton(
            icon = Res.drawable.ic_circle,
            internalPadding = 6.dp,
            enabled = pagerButtonEnabled,
            onClick = onPagerButtonClick,
        )

        GlassPanelButton(
            icon = Res.drawable.ic_four_circles,
            internalPadding = 6.dp,
            enabled = gridButtonEnabled,
            onClick = onGridButtonClick,
        )

        GlassPanelDivider()

        GlassPanelButton(
            icon = Res.drawable.ic_settings,
            internalPadding = 2.dp,
            enabled = settingsButtonEnabled,
            onClick = onSettingsButtonClick,
        )
    }
}