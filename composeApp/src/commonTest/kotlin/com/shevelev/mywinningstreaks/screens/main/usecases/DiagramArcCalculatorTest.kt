package com.shevelev.mywinningstreaks.screens.main.usecases

import com.shevelev.mywinningstreaks.coreentities.Status
import com.shevelev.mywinningstreaks.shared.usecases.DiagramArcCalculatorImpl
import com.shevelev.mywinningstreaks.storage.database.dto.Streak
import com.shevelev.mywinningstreaks.storage.database.dto.StreakInterval
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

@OptIn(ExperimentalTime::class)
class DiagramArcCalculatorTest {
    private val testSubject = DiagramArcCalculatorImpl()

    @Test
    fun `intervals list is empty`() = runTest {
        // Arrange
        val streak = createStreak()

        // Act
        val result = testSubject.calculateArcs(streak, daysToShow = 30)

        // Assert
        assertEquals(1, result.size)

        with(result[0]) {
            assertEquals(0f, from)
            assertEquals(1f, to)
            assertEquals(Status.Unknown, status)
        }
    }

    @Test
    fun `total days are the same - one interval`() = runTest {
        // Arrange
        val streak = createStreak(
            createInterval(
                fromDate = createDate(month = 1, day = 1),
                toDate = createDate(month = 1, day = 5),
                status = Status.Marked,
            )
        )

        // Act
        val result = testSubject.calculateArcs(streak, daysToShow = 5)

        // Assert
        assertEquals(1, result.size)

        with(result[0]) {
            assertEquals(0f, from)
            assertEquals(1f, to)
            assertEquals(Status.Marked, status)
        }
    }

    @Test
    fun `total days are the same - one interval in one day`() = runTest {
        // Arrange
        val streak = createStreak(
            createInterval(
                fromDate = createDate(month = 1, day = 1),
                toDate = createDate(month = 1, day = 1),
                status = Status.Marked,
            )
        )

        // Act
        val result = testSubject.calculateArcs(streak, daysToShow = 1)

        // Assert
        assertEquals(1, result.size)

        with(result[0]) {
            assertEquals(0f, from)
            assertEquals(1f, to)
            assertEquals(Status.Marked, status)
        }
    }

    @Test
    fun `total days are the same - two intervals`() = runTest {
        // Arrange
        val streak = createStreak(
            createInterval(
                fromDate = createDate(month = 1, day = 1),
                toDate = createDate(month = 1, day = 5),
                status = Status.Marked,
            ),
            createInterval(
                fromDate = createDate(month = 1, day = 6),
                toDate = createDate(month = 1, day = 10),
                status = Status.Sick,
            ),
        )

        // Act
        val result = testSubject.calculateArcs(streak, daysToShow = 10)

        // Assert
        assertEquals(2, result.size)

        with(result[0]) {
            assertEquals(0f, from)
            assertEquals(0.5f, to)
            assertEquals(Status.Marked, status)
        }

        with(result[1]) {
            assertEquals(0.5f, from)
            assertEquals(1f, to)
            assertEquals(Status.Sick, status)
        }
    }

    @Test
    fun `total days are greater`() = runTest {
        // Arrange
        val streak = createStreak(
            createInterval(
                fromDate = createDate(month = 1, day = 1),
                toDate = createDate(month = 1, day = 5),
                status = Status.Marked,
            ),
            createInterval(
                fromDate = createDate(month = 1, day = 6),
                toDate = createDate(month = 1, day = 10),
                status = Status.Sick,
            ),
        )

        // Act
        val result = testSubject.calculateArcs(streak, daysToShow = 20)

        // Assert
        assertEquals(3, result.size)

        with(result[0]) {
            assertEquals(0f, from)
            assertEquals(0.25f, to)
            assertEquals(Status.Marked, status)
        }

        with(result[1]) {
            assertEquals(0.25f, from)
            assertEquals(0.5f, to)
            assertEquals(Status.Sick, status)
        }

        with(result[2]) {
            assertEquals(0.5f, from)
            assertEquals(1f, to)
            assertEquals(Status.Unknown, status)
        }
    }

    @Test
    fun `total days are less - even case`() = runTest {
        // Arrange
        val streak = createStreak(
            createInterval(
                fromDate = createDate(month = 1, day = 1),
                toDate = createDate(month = 1, day = 5),
                status = Status.Marked,
            ),
            createInterval(
                fromDate = createDate(month = 1, day = 6),
                toDate = createDate(month = 1, day = 10),
                status = Status.Sick,
            ),
            createInterval(
                fromDate = createDate(month = 1, day = 11),
                toDate = createDate(month = 1, day = 15),
                status = Status.Marked,
            ),
        )

        // Act
        val result = testSubject.calculateArcs(streak, daysToShow = 10)

        // Assert
        assertEquals(2, result.size)

        with(result[0]) {
            assertEquals(0f, from)
            assertEquals(0.5f, to)
            assertEquals(Status.Sick, status)
        }

        with(result[1]) {
            assertEquals(0.5f, from)
            assertEquals(1f, to)
            assertEquals(Status.Marked, status)
        }
    }

    @Test
    fun `total days are less - not even case`() = runTest {
        // Arrange
        val streak = createStreak(
            createInterval(
                fromDate = createDate(month = 1, day = 1),
                toDate = createDate(month = 1, day = 5),
                status = Status.Marked,
            ),
            createInterval(
                fromDate = createDate(month = 1, day = 6),
                toDate = createDate(month = 1, day = 15),
                status = Status.Sick,
            ),
            createInterval(
                fromDate = createDate(month = 1, day = 16),
                toDate = createDate(month = 1, day = 20),
                status = Status.Marked,
            ),
        )

        // Act
        val result = testSubject.calculateArcs(streak, daysToShow = 10)

        // Assert
        assertEquals(2, result.size)

        with(result[0]) {
            assertEquals(0f, from)
            assertEquals(0.5f, to)
            assertEquals(Status.Sick, status)
        }

        with(result[1]) {
            assertEquals(0.5f, from)
            assertEquals(1f, to)
            assertEquals(Status.Marked, status)
        }
    }

    private fun createInterval(fromDate: Instant, toDate: Instant, status: Status) = StreakInterval(
        id = 42,
        fromDate,
        toDate,
        status,
    )

    private fun createStreak(vararg intervals: StreakInterval) = Streak(
        id = 42,
        title = "",
        marked = true,
        intervals = intervals.toList(),
    )

    @Suppress("SameParameterValue")
    private fun createDate(month: Int, day: Int) =
        LocalDate(2025, month, day).atStartOfDayIn(TimeZone.currentSystemDefault())
}
