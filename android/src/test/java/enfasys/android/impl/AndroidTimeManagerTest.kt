package enfasys.android.impl

import enfasys.android.core.TimeManager
import org.junit.Before
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AndroidTimeManagerTest : BaseTest() {
    private val fixedEpochMillis = 1578405600000L   //01/07/2020 2:00pm (UTC)
    private val fixedUtcDate = "2020-01-07T14:00:00Z"
    private val fixedUtcDateWithOffset = "2020-01-07T14:00:00+00:00"
    private val format = "HH:mm"
    private val offsetForAmericaLima = 5 * 60 * 60 * 1000L
    private val oneDay = 24 * 60 * 60 * 1000L

    private val timeManager: TimeManager =
        AndroidTimeManager()

    @Before
    fun setLocale() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Lima"))
    }

    @Test
    fun `Returns the time in UTC and in current timezone`() {
        val timeUTC = timeManager.getTime(utc = true)
        val timeCurrentTimeZone = timeManager.getTime(utc = false)

        assertTrue(timeUTC - timeCurrentTimeZone <= offsetForAmericaLima)
    }

    @Test
    fun `Returns the start and end of the day in UTC and in current timezone`() {
        val (startUtc, endUtc) = timeManager.getStartAndEndOfDay(utc = true)
        val (startTimezone, endInTimezone) = timeManager.getStartAndEndOfDay(utc = false)

        assertEquals(endUtc - startUtc, oneDay - 1)
        assertEquals(endInTimezone - startTimezone, oneDay - 1)
        assertTrue(endUtc - endInTimezone <= offsetForAmericaLima)
        assertTrue(startUtc - startTimezone <= offsetForAmericaLima)
    }

    @Test
    fun `Formats millis in UTC`() {
        val dateUtc = timeManager.formatEpochMillis(fixedEpochMillis, format, asUtc = true)

        assertEquals("14:00", dateUtc)
    }

    @Test
    fun `Formats millis in current timezone`() {
        val dateTimezone = timeManager.formatEpochMillis(fixedEpochMillis, format, asUtc = false)

        assertEquals("09:00", dateTimezone)
    }

    @Test
    fun `Formats date in UTC`() {
        val dateUtc = timeManager.formatUTCDate(fixedUtcDate, format, asUtc = true)

        assertEquals("14:00", dateUtc)
    }

    @Test
    fun `Formats date in current timezone`() {
        val dateTimezone = timeManager.formatUTCDate(fixedUtcDate, format, asUtc = false)

        assertEquals("09:00", dateTimezone)
    }

    @Test
    fun `Returns timezone offset`() {
        val offset = timeManager.getTimezoneOffset()

        assertEquals(-offsetForAmericaLima, offset)
    }

    @Test
    fun `Formats local date in UTC`() {
        val dateWithZ = timeManager.formatAsLocalDate(fixedUtcDate, format, asUtc = true)
        val dateWithOffset =
            timeManager.formatAsLocalDate(fixedUtcDateWithOffset, format, asUtc = true)

        assertEquals("19:00", dateWithZ)
        assertEquals("19:00", dateWithOffset)
    }

    @Test
    fun `Formats local date in current timezone`() {
        val dateWithZ = timeManager.formatAsLocalDate(fixedUtcDate, format, asUtc = false)
        val dateWithOffset =
            timeManager.formatAsLocalDate(fixedUtcDateWithOffset, format, asUtc = false)

        assertEquals("14:00", dateWithZ)
        assertEquals("14:00", dateWithOffset)
    }
}
