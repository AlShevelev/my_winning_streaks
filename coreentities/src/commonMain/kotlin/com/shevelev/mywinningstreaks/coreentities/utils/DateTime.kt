package com.shevelev.mywinningstreaks.coreentities.utils

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
object DateTimeUtils {
    private val timeZone by lazy { TimeZone.currentSystemDefault() }

    fun getNowDate(): Instant =
        dateToInstant(Clock.System.now().toLocalDateTime(timeZone).date)

    fun createDate(year: Int, month: Int, day: Int) = dateToInstant(LocalDate(year, month, day))

    fun getAbsoluteNowInMillis() = Clock.System.now().toEpochMilliseconds()

    fun instantToMillis(instant: Instant) = instant.toEpochMilliseconds()

    fun millisToInstant(millis: Long) = Instant.fromEpochMilliseconds(millis)

    private fun dateToInstant(date: LocalDate) =
        date.atStartOfDayIn(timeZone)
}

