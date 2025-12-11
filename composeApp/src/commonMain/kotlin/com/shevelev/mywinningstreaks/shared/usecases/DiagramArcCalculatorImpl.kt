package com.shevelev.mywinningstreaks.shared.usecases

import com.shevelev.mywinningstreaks.coreentities.Status
import com.shevelev.mywinningstreaks.shared.usecases.dto.StreakArc
import com.shevelev.mywinningstreaks.storage.database.dto.Streak
import com.shevelev.mywinningstreaks.storage.database.dto.StreakInterval
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
internal class DiagramArcCalculatorImpl(): DiagramArcCalculator {
    override suspend fun calculateArcs(dbStreak: Streak, daysToShow: Int): List<StreakArc> {
        if (dbStreak.intervals.isEmpty()) {
            return listOf(
                StreakArc(
                    from = 0f,
                    to = 1f,
                    status = Status.Unknown,
                )
            )
        }

        val daysTotal = dbStreak.intervals.sumOf { it.wholeDays() }

        return when {
            daysTotal == daysToShow -> intervalsToArcSame(dbStreak.intervals, daysToShow)
            daysTotal < daysToShow -> intervalsToArcLessThanDays(dbStreak.intervals, daysToShow)
            daysTotal > daysToShow -> intervalsToArcGreaterThanDays(dbStreak.intervals, daysToShow)
            else -> throw UnsupportedOperationException("This case is not supported")
        }
    }

    private fun intervalsToArcSame(intervals: List<StreakInterval>, daysToShow: Int): List<StreakArc> {
        var lastTo = 0f

        val result = mutableListOf<StreakArc>()

        for (i in intervals.indices) {
            val part = intervals[i].wholeDays().toFloat() / daysToShow

            result.add(
                intervals[i].toArc(
                    from = lastTo,
                    to = if (i != intervals.lastIndex) lastTo + part else 1f,
                )
            )

            lastTo+= part
        }

        return result
    }

    private fun intervalsToArcLessThanDays(
        intervals: List<StreakInterval>,
        daysToShow: Int,
    ): List<StreakArc> {
        var lastTo = 0f

        val result = mutableListOf<StreakArc>()

        for (i in intervals.indices) {
            val part = intervals[i].wholeDays().toFloat() / daysToShow

            result.add(
                intervals[i].toArc(
                    from = lastTo,
                    to = lastTo + part,
                )
            )

            lastTo+= part
        }

        result.add(
            StreakArc(
                from = lastTo,
                to = 1f,
                status = Status.Unknown,
            )
        )

        return result
    }

    private fun intervalsToArcGreaterThanDays(
        intervals: List<StreakInterval>,
        daysToShow: Int,
    ): List<StreakArc> {
        var lastTo = 0f

        var acc = daysToShow

        var firstIndex = 0

        val result = mutableListOf<StreakArc>()

        for (i in intervals.lastIndex downTo 0) {
            acc -= intervals[i].wholeDays()

            if (acc <=0) {
                firstIndex = i
                break
            }
        }

        val part = (intervals[firstIndex].wholeDays()+acc).toFloat() / daysToShow
        result.add(
            intervals[firstIndex].toArc(
                from = lastTo,
                to = lastTo + part,
            )
        )
        lastTo+= part

        for (i in firstIndex+1..intervals.lastIndex) {
            val part = intervals[i].wholeDays().toFloat() / daysToShow

            result.add(
                intervals[i].toArc(
                    from = lastTo,
                    to = if (i == intervals.lastIndex) 1f else lastTo + part,
                )
            )

            lastTo+= part
        }

        return result
    }

    private fun StreakInterval.wholeDays(): Int = ((toDate - fromDate).inWholeDays + 1).toInt()

    private fun StreakInterval.toArc(from: Float, to: Float) = StreakArc(from, to, status)
}