package enfasys.android.impl

import enfasys.android.core.TimeManager
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidTimeManager @Inject constructor() : TimeManager {
    override fun getTime(utc: Boolean): Long {
        return if (utc) {
            Instant.now().toEpochMilli()
        } else {
            ZonedDateTime.now().toEpochMilli()
        }
    }

    override fun getStartAndEndOfDay(utc: Boolean): Pair<Long, Long> {
        val now = ZonedDateTime.now()
        val beginDay = now.toLocalDate().atStartOfDay()
        val endDay = now.toLocalDate().atTime(LocalTime.MAX)

        return if (utc) {
            val start = beginDay.toInstant(ZoneOffset.UTC).toEpochMilli()
            val end = endDay.toInstant(ZoneOffset.UTC).toEpochMilli()
            Pair(start, end)
        } else {
            val start = beginDay.toInstant(now.offset).toEpochMilli()
            val end = endDay.toInstant(now.offset).toEpochMilli()
            Pair(start, end)
        }
    }

    override fun getTimezoneOffset(): Long {
        return ZonedDateTime.now().offset.totalSeconds * MILLISECONDS
    }

    override fun formatEpochMillis(millis: Long, format: String, asUtc: Boolean): String {
        val formatter = DateTimeFormatter.ofPattern(format)
        val zone = if (asUtc) {
            ZoneId.of("UTC")
        } else {
            ZoneId.systemDefault()
        }
        return Instant.ofEpochMilli(millis)
            .atZone(zone)
            .format(formatter)
    }

    override fun formatUTCDate(date: String, format: String, asUtc: Boolean): String {
        val millis = toEpochMilli(date)
        return formatEpochMillis(millis, format, asUtc)
    }

    override fun formatAsLocalDate(date: String, format: String, asUtc: Boolean): String {
        val localDateTime = parseLocalDateTime(date)
        val formatter = DateTimeFormatter.ofPattern(format)
        return if (asUtc) {
            val utc = localDateTime
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)
            utc.format(formatter)
        } else {
            localDateTime.format(formatter)
        }
    }

    private fun ZonedDateTime.toEpochMilli(): Long {
        return this.toLocalDateTime().toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    private fun toEpochMilli(date: String): Long {
        return ZonedDateTime.parse(date)
            .toInstant()
            .toEpochMilli()
    }

    private fun parseLocalDateTime(localDate: String): LocalDateTime {
        val clearLocalDate = clearLocalDate(localDate)
        return LocalDateTime.parse(
            clearLocalDate,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        )
    }

    private fun clearLocalDate(localDate: String): String {
        val withoutMilliseconds = localDate.replace(".000", "")

        val withoutT = withoutMilliseconds.replace("T", " ")
        val withoutZ = withoutT.replace("Z", "")

        return withoutZ.substringBefore("+")
    }

    companion object {
        const val MILLISECONDS = 1000L
    }
}
