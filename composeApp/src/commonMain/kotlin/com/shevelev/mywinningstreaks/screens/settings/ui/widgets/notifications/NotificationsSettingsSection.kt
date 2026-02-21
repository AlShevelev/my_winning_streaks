package com.shevelev.mywinningstreaks.screens.settings.ui.widgets.notifications

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shevelev.mywinningstreaks.screens.settings.viewmodel.SettingsScreenState
import com.shevelev.mywinningstreaks.shared.ui.theme.LocalDimensions
import mywinningstreaks.composeapp.generated.resources.Res
import mywinningstreaks.composeapp.generated.resources.how_many_times
import mywinningstreaks.composeapp.generated.resources.how_often
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun NotificationsSettingsSection(
    modifier: Modifier = Modifier,
    stateValue: SettingsScreenState.Data,
    setHowOften: (Int) -> Unit,
    setHowManyTimes: (Int) -> Unit,
) {
    val dimensions = LocalDimensions.current

    Text(
        text = stringResource(Res.string.how_often),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = dimensions.paddingDouble),
    )

    HowOftenSelection(
        howOftenValues = stateValue.howOftenValues,
        howOften = stateValue.howOften,
        onValueChange = { setHowOften(it) },
        modifier = Modifier
            .padding(horizontal = dimensions.paddingDouble)
            .fillMaxWidth(),
    )

    Text(
        text = stringResource(Res.string.how_many_times),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = dimensions.paddingDouble),
    )

    HowManyTimesSelection(
        howManyTimes = stateValue.howManyTimes,
        howManyTimesValues = stateValue.howManyTimesValues,
        onValueChange = { setHowManyTimes(it) },
        modifier = Modifier
            .padding(horizontal = dimensions.paddingDouble)
            .fillMaxWidth(),
    )
}