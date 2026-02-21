package com.shevelev.mywinningstreaks.screens.settings.ui.widgets.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.TextSlider
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.once
import mywinningstreaks.composeapp.generated.resources.three_times
import mywinningstreaks.composeapp.generated.resources.twice
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HowManyTimesSelection(
    howManyTimesValues: List<Int>,
    howManyTimes: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
) {
    TextSlider(
        values = howManyTimesValues,
        value = howManyTimes,
        modifier = modifier,
        label = {
            when (it) {
                1 -> stringResource(Res.string.once)
                2 -> stringResource(Res.string.twice)
                3 -> stringResource(Res.string.three_times)
                else -> ""
            }
        },
        onValueChange = onValueChange
    )
}
