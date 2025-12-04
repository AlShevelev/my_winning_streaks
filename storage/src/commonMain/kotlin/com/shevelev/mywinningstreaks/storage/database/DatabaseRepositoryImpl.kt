package com.shevelev.mywinningstreaks.storage.database

import com.shevelev.mywinningstreaks.coreentities.Status
import com.shevelev.mywinningstreaks.storage.api.database.AppStorageDatabase
import com.shevelev.mywinningstreaks.storage.database.dto.AllStreaks
import com.shevelev.mywinningstreaks.storage.database.dto.Streak
import com.shevelev.mywinningstreaks.storage.database.dto.StreakInterval
import com.shevelev.mywinningstreaks.storage.database.factory.DatabaseDriverFactory
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@OptIn(ExperimentalTime::class)
internal class DatabaseRepositoryImpl(
    private var driverFactory: DatabaseDriverFactory,
    private val dispatcher: CoroutineDispatcher,
) : DatabaseRepository {
    private val queries by lazy {
        AppStorageDatabase(driverFactory.createDriver()).appStorageQueries
    }

    override suspend fun getAllStreaks(): AllStreaks = withContext(dispatcher) {
        val streaks = queries.readAllStreaks().executeAsList()
        val intervals = queries.readAllStreakIntervals().executeAsList()

        AllStreaks(
            streaks = streaks.map {
                Streak(
                    id = it.streak_id,
                    sortingOrder = it.sorting_order.toInt(),
                    title = it.title,
                    marked = it.marked != 0L
                )
            },
            streakIntervals = intervals.map {
                StreakInterval(
                    id = it.streak_interval_id,
                    streakId = it.streak_id,
                    fromDate = Instant.fromEpochMilliseconds(it.from_date),
                    toDate = Instant.fromEpochMilliseconds(it.to_date),
                    type = when(it.type) {
                        0L -> Status.Marked
                        1L -> Status.Skipped
                        2L -> Status.Sick
                        else -> throw IllegalStateException("Unknown interval type: ${it.type}")
                    }
                )
            },
        )
    }
}