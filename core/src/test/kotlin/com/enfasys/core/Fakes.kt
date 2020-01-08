package com.enfasys.core

internal class FakeBaseRepository(logger: Logger, networkVerifier: NetworkVerifier) :
    BaseRepository(logger, networkVerifier)

internal class FakeLogger : Logger {
    var wasCalled: Boolean = false

    override fun e(throwable: Throwable, tag: String?) {
        wasCalled = true
    }

    override fun d(message: String, tag: String?) = Unit
}

internal class FakeNetworkVerifier(private val shouldReturnTrue: Boolean) : NetworkVerifier {
    override fun isConnectionAvailable() = shouldReturnTrue
}

internal class FakeTimeManager(private val fixedTime: Long) : TimeManager {
    override fun getTime(utc: Boolean): Long = fixedTime

    override fun getStartAndEndOfDay(utc: Boolean): Pair<Long, Long> = Pair(1, 1)

    override fun getTimezoneOffset(): Long = 1

    override fun formatEpochMillis(millis: Long, format: String, asUtc: Boolean): String = ""

    override fun formatUTCDate(date: String, format: String, asUtc: Boolean): String = ""

    override fun formatAsLocalDate(date: String, format: String, asUtc: Boolean): String = ""
}