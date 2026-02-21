package com.shevelev.mywinningstreaks.screens.settings.ui.widgets.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shevelev.mywinningstreaks.screens.settings.ui.widgets.TextSlider
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.days_10
import mywinningstreaks.composeapp.generated.resources.days_14
import mywinningstreaks.composeapp.generated.resources.days_180
import mywinningstreaks.composeapp.generated.resources.days_30
import mywinningstreaks.composeapp.generated.resources.days_365
import mywinningstreaks.composeapp.generated.resources.days_60
import mywinningstreaks.composeapp.generated.resources.days_7
import mywinningstreaks.composeapp.generated.resources.days_90
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RecentDaysSelection(
    recentDaysToShowValues: List<Int>,
    recentDaysToShow: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
) {
    TextSlider(
        values = recentDaysToShowValues,
        value = recentDaysToShow,
        modifier = modifier,
        label = {
            when (it) {
                7 -> stringResource(Res.string.days_7)
                10 -> stringResource(Res.string.days_10)
                14 -> stringResource(Res.string.days_14)
                30 -> stringResource(Res.string.days_30)
                60 -> stringResource(Res.string.days_60)
                90 -> stringResource(Res.string.days_90)
                180 -> stringResource(Res.string.days_180)
                365 -> stringResource(Res.string.days_365)
                else -> ""

            }
        },
        onValueChange = {
            println(it)
            onValueChange(it)
        }
    )
}
