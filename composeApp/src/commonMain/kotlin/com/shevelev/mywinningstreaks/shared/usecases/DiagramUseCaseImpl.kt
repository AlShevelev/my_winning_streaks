package com.shevelev.mywinningstreaks.shared.usecases

import com.shevelev.mywinningstreaks.coreentities.Status
import com.shevelev.mywinningstreaks.shared.usecases.dto.Streak
import com.shevelev.mywinningstreaks.storage.database.DatabaseRepository
import com.shevelev.mywinningstreaks.storage.database.dto.Streak as DbStreak
import com.shevelev.mywinningstreaks.storage.database.dto.StreakInterval as DbStreakInterval
import com.shevelev.mywinningstreaks.storage.settings.SettingsRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
internal class DiagramUseCaseImpl(
    private val settingsRepository: SettingsRepository,
    private val databaseRepository: DatabaseRepository,
    private val diagramArcCalculator: DiagramArcCalculator,
) : DiagramUseCase {
    private val _diagrams = MutableStateFlow<List<Streak>?>(null)
    override val diagrams: Flow<List<Streak>?> = _diagrams.asStateFlow()

    override suspend fun init() {
        if (_diagrams.value != null) {
            return
        }

        val daysToShow = settingsRepository.getDaysToShow()
        val dbAllStreaks = databaseRepository.getAllStreaks()

        val result = dbAllStreaks.map { it.toStreak(daysToShow) }

        _diagrams.emit(result)
    }

    override suspend fun addStreak(title: String) {
        val timeZone = TimeZone.currentSystemDefault()

        val streakId = TimeSource.Monotonic.markNow().elapsedNow().inWholeNanoseconds

        val date = Clock.System.now().toLocalDateTime(timeZone).date.atStartOfDayIn(timeZone)

        val dbStreak = DbStreak(
            id = streakId,
            sortingOrder = Long.MIN_VALUE - streakId,
            title = title,
            marked = true,
            intervals = listOf(
                DbStreakInterval(
                    id = streakId,
                    fromDate = date,
                    toDate = date,
                    status = Status.Marked,
                )
            ),
        )

        databaseRepository.addStreak(dbStreak)

        val daysToShow = settingsRepository.getDaysToShow()
        val streak = dbStreak.toStreak(daysToShow)

        val newValue = (_diagrams.value ?: emptyList()).toMutableList().apply {
            add(0, streak)
        }
        _diagrams.emit(newValue)
    }

    private suspend fun DbStreak.toStreak(daysToShow: Int): Streak =
        Streak(
            id = id,
            title = title,
            lastIntervalId = intervals.last().id,
            arcs = diagramArcCalculator.calculateArcs(this, daysToShow)
        )
}