package com.shevelev.mywinningstreaks.coreentities.utils

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
object DateTimeUtils {
    private val timeZone by lazy { TimeZone.currentSystemDefault() }

    fun getNowLocalDate(): LocalDate = Clock.System.now().toLocalDateTime(timeZone).date

    fun createLocalDate(year: Int, month: Int, day: Int) = LocalDate(year, month, day)

    fun getAbsoluteNowInMillis() = Clock.System.now().toEpochMilliseconds()

    /*  According the doc
        https://github.com/Kotlin/kotlinx-datetime?tab=readme-ov-file#converting-instant-and-local-datetime-to-and-from-the-iso-8601-string
        it will be a ISO 8601 string without time part */
    fun localDateToString(localDate: LocalDate) = localDate.toString()

    fun stringToLocalDate(value: String) = LocalDate.parse(value)

    /*  According the doc
        https://github.com/Kotlin/kotlinx-datetime?tab=readme-ov-file#converting-instant-and-local-datetime-to-and-from-the-iso-8601-string
        it will be a ISO 8601 string without time part */
    fun localTimeToString(localTime: LocalTime) = localTime.toString()

    fun stringToLocalTime(value: String) = LocalTime.parse(value)
}

