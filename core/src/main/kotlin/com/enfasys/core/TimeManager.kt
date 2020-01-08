package com.enfasys.core

interface TimeManager {
    fun getTime(utc: Boolean = true): Long
    fun getStartAndEndOfDay(utc: Boolean = true): Pair<Long, Long>
    fun getTimezoneOffset(): Long
    fun formatEpochMillis(millis: Long, format: String, asUtc: Boolean = true): String

    /**
     * Formats UTC date according to format. Date must be compliant with ISO 8601
     * For example: 2020-01-07T14:00:00Z or 1994-11-05T08:15:30+05:30
     * @return date formatted according to the format
     */
    fun formatUTCDate(date: String, format: String, asUtc: Boolean = true): String


    /**
     * Formats date as a local date according to format. Date must be compliant with ISO 8601
     * For example: 2020-01-07T14:00:00Z or 1994-11-05T08:15:30+05:30
     * During the execution of this method, timezone and offset are removed from date,
     * so only the date and time part are considered during formatting.
     * @return date formatted according to the format
     */
    fun formatAsLocalDate(date: String, format: String, asUtc: Boolean = false): String
}