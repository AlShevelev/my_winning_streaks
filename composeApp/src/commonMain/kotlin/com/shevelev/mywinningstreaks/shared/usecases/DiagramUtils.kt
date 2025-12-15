package com.shevelev.mywinningstreaks.shared.usecases

import com.shevelev.mywinningstreaks.storage.database.dto.StreakInterval
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal fun StreakInterval.wholeDays(): Int = ((toDate - fromDate).inWholeDays + 1).toInt()