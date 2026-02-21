package com.shevelev.mywinningstreaks.screens.settings.ui.widgets.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.TextSlider
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.every_10_minutes
import mywinningstreaks.composeapp.generated.resources.every_15_minutes
import mywinningstreaks.composeapp.generated.resources.every_5_minutes
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HowOftenSelection(
    howOftenValues: List<Int>,
    howOften: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
) {
    TextSlider(
        values = howOftenValues,
        value = howOften,
        modifier = modifier,
        label = {
            when (it) {
                5 -> stringResource(Res.string.every_5_minutes)
                10 -> stringResource(Res.string.every_10_minutes)
                15 -> stringResource(Res.string.every_15_minutes)
                else -> ""
            }
        },
        onValueChange = onValueChange
    )
}
