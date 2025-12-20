package com.shevelev.mywinningstreaks.storage.database

import com.shevelev.mywinningstreaks.coreentities.Status
import com.shevelev.mywinningstreaks.storage.api.database.AppStorageDatabase
import com.shevelev.mywinningstreaks.storage.api.database.Streak as DbStreak
import com.shevelev.mywinningstreaks.storage.api.database.Streak_interval as DbStreakInterval
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

    override suspend fun getAllStreaks(): List<Streak> = withContext(dispatcher) {
        val streaks = queries.readAllStreaks().executeAsList()
        val intervals = queries.readAllStreakIntervals().executeAsList()

        val acc = mutableMapOf<Long, Pair<DbStreak, MutableList<DbStreakInterval>>>()

        for (s in streaks) {
            acc[s.streak_id] = Pair(s, mutableListOf())
        }

        intervals.sortedBy { it.from_date }.forEach {
            acc[it.streak_id]?.second?.add(it)
        }

        acc.values.sortedBy { it.first.sorting_order }.map {
            Streak(
                id = it.first.streak_id,
                sortingOrder = it.first.sorting_order,
                title = it.first.title,
                marked = it.first.marked != 0L,
                intervals = it.second.map { interval ->
                    StreakInterval(
                        id = interval.streak_interval_id,
                        fromDate = Instant.fromEpochMilliseconds(interval.from_date),
                        toDate = Instant.fromEpochMilliseconds(interval.to_date),
                        status = when (interval.type) {
                            0L -> Status.Won
                            1L -> Status.Failed
                            2L -> Status.Sick
                            else -> throw IllegalStateException("Unknown interval type: ${interval.type}")
                        }
                    )
                }
            )
        }
    }

    override suspend fun addStreak(streak: Streak) {
        queries.createStreak(
            streak_id = streak.id,
            sorting_order = streak.sortingOrder,
            title = streak.title,
            marked = if (streak.marked) 1L else 0L,
        )

        for (interval in streak.intervals) {
            queries.createStreakInterval(
                streak_interval_id = interval.id,
                streak_id = streak.id,
                from_date = interval.fromDate.toEpochMilliseconds(),
                to_date = interval.toDate.toEpochMilliseconds(),
                type = when(interval.status) {
                    Status.Won -> 0L
                    Status.Failed -> 1L
                    Status.Sick -> 2L
                    else -> throw IllegalStateException("Unknown interval status: ${interval.status}")
                }
            )
        }
    }

    override suspend fun updateStreakTitle(id: Long, title: String) {
        queries.updateStreakTitle(title, id)
    }

    override suspend fun deleteStreak(id: Long) {
        queries.deleteStreakIntervalsForStreak(id)
        queries.deleteStreak(id)
    }
}