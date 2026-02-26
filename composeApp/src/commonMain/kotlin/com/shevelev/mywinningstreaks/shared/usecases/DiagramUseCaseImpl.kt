package com.shevelev.mywinningstreaks.shared.usecases

import com.shevelev.mywinningstreaks.coreentities.Status
import com.shevelev.mywinningstreaks.coreentities.utils.DateTimeUtils
import com.shevelev.mywinningstreaks.shared.usecases.dto.Streak
import com.shevelev.mywinningstreaks.storage.database.DatabaseRepository
import com.shevelev.mywinningstreaks.storage.database.dto.Streak as DbStreak
import com.shevelev.mywinningstreaks.storage.database.dto.StreakInterval
import com.shevelev.mywinningstreaks.storage.database.dto.StreakInterval as DbStreakInterval
import com.shevelev.mywinningstreaks.storage.settings.SettingsRepository
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

@OptIn(ExperimentalTime::class)
internal class DiagramUseCaseImpl(
    private val settingsRepository: SettingsRepository,
    private val databaseRepository: DatabaseRepository,
    private val diagramArcCalculator: DiagramArcCalculator,
) : DiagramUseCase {
    private val _diagrams = MutableStateFlow<List<Streak>?>(null)
    override val diagrams = _diagrams.asStateFlow()

    override suspend fun init() {
        val daysToShow = settingsRepository.getDaysToShow()
        val dbAllStreaks = databaseRepository.getAllStreaks()

        val dateNow = DateTimeUtils.nowLocalDate

        val allStreaks = dbAllStreaks.map {
            it.toStreak(daysToShow, dateNow)
        }.toMutableList()

        _diagrams.emit(allStreaks)
    }

    override suspend fun addStreak(title: String) {
        val streakId = Random.nextLong()

        val dateNow = DateTimeUtils.nowLocalDate

        val absoluteNow = DateTimeUtils.getAbsoluteNowInMillis()

        val dbStreak = DbStreak(
            id = streakId,
            sortingOrder = Long.MIN_VALUE - absoluteNow,
            title = title,
            intervals = listOf(
                DbStreakInterval(
                    id = streakId,
                    fromDate = dateNow,
                    toDate = dateNow,
                    status = Status.Won,
                )
            ),
        )

        databaseRepository.addStreak(dbStreak)

        val daysToShow = settingsRepository.getDaysToShow()
        val streak = dbStreak.toStreak(daysToShow, dateNow)

        val newValue = (_diagrams.value ?: emptyList()).toMutableList().apply {
            add(0, streak)
        }
        _diagrams.emit(newValue)
    }

    override suspend fun updateStreakTitle(id: Long, title: String) {
        val streaks = _diagrams.value ?: return
        val streakIndex = getStreakIndexById(id) ?: return

        databaseRepository.updateStreakTitle(id, title)

        val newValue = streaks.toMutableList().also {
            it[streakIndex] = streaks[streakIndex].copy(title = title)
        }
        _diagrams.emit(newValue)
    }

    override suspend fun deleteStreak(id: Long) {
        val streaks = _diagrams.value ?: return

        val streakIndex = streaks.indexOfFirst { it.id == id }
        if (streakIndex == -1) return

        databaseRepository.deleteStreak(id)

        val newValue = streaks.toMutableList().also {
            it.removeAt(streakIndex)
        }
        _diagrams.emit(newValue)
    }

    override suspend fun markStreak(id: Long, status: Status) {
        val streaks = _diagrams.value?.toMutableList() ?: return

        markStreak(streaks, id, status)

        _diagrams.emit(streaks)
    }

    private suspend fun markStreak(allStreaks: MutableList<Streak>, id: Long, status: Status) {
        val streakIndex = getStreakIndexById(allStreaks, id) ?: return
        val streak = allStreaks[streakIndex]

        val dateNow = DateTimeUtils.nowLocalDate
        val dateLastTo = streak.lastIntervalToDate

        if (dateNow <= dateLastTo) return   // A time zone has been changed etc.

        if (streak.arcs.last { it.status != Status.Unknown }.status == status) {
            databaseRepository.updateToValueOfStreakInterval(streak.lastIntervalId, dateNow)
        } else {
            databaseRepository.addStreakInterval(
                StreakInterval(
                    id = Random.nextLong(),
                    fromDate = dateLastTo.plus(1, DateTimeUnit.DAY),
                    toDate = dateNow,
                    status = status,
                ),
                streak.id,
            )
        }

        val dbStreak = databaseRepository.getStreak(id)
        val daysToShow = settingsRepository.getDaysToShow()

        allStreaks[streakIndex] = dbStreak.toStreak(daysToShow, dateNow)
    }

    private fun getStreakIndexById(id: Long): Int? {
        val streaks = _diagrams.value ?: return null
        return getStreakIndexById(streaks, id)
    }

    private fun getStreakIndexById(allStreaks: List<Streak>, id: Long): Int? =
        allStreaks.indexOfFirst { it.id == id }.takeIf { it != -1 }

    private suspend fun DbStreak.toStreak(daysToShow: Int, dateNow: LocalDate): Streak {
        var totalDays = 0
        var winDays = 0
        var failDays = 0
        var sickDays = 0

        for (i in intervals) {
            val days = i.wholeDays()

            totalDays += days

            when (i.status) {
                Status.Won -> winDays += days
                Status.Failed -> failDays += days
                Status.Sick -> sickDays += days
                else -> throw UnsupportedOperationException("Unsupported status: ${i.status}")
            }
        }

        val lastInterval = intervals.last()

        return Streak(
            id = id,
            title = title,
            lastIntervalId = lastInterval.id,
            lastIntervalToDate = lastInterval.toDate,
            totalDaysToShow = daysToShow,
            totalDays = totalDays,
            winDays = winDays,
            failDays = failDays,
            sickDays = sickDays,
            canMark = dateNow > lastInterval.toDate,
            arcs = diagramArcCalculator.calculateArcs(this, daysToShow)
        )
    }
}