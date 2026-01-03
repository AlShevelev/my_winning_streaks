package com.shevelev.mywinningstreaks.shared.usecases

import com.shevelev.mywinningstreaks.storage.database.dto.StreakInterval
import kotlin.time.ExperimentalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

@OptIn(ExperimentalTime::class)
internal fun StreakInterval.wholeDays(): Int {
    val timeZone = TimeZone.currentSystemDefault()

    val toInstant = toDate.atStartOfDayIn(timeZone)
    val fromInstant = fromDate.atStartOfDayIn(timeZone)

    return ((toInstant - fromInstant).inWholeDays + 1).toInt()
}