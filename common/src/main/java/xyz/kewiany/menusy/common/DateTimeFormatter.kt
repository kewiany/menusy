package xyz.kewiany.menusy.common

import org.slf4j.LoggerFactory
import java.time.Clock
import java.time.DayOfWeek
import java.time.OffsetTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*
import javax.inject.Inject

/**
 * Intentionally does **not** handle user preferences for 12/24-hour clock or order of date/month/year, as per requirements.
 * Does **not** support [OffsetTime], because it does not support overriding timezone during formatting.
 *
 * Formats:
 * - [day of week with time][formatDayOfWeekWithTime]: `Monday, 12:58`
 * - [long date][formatLongDate]: `2019-01-31`
 * - [long date range][formatLongDateRange]: `2019-01-01 - 2019-01-31`
 * - [long date time][formatLongDateTime]: `2019-01-31, 14:21`
 * - [long date time range][formatLongDateTimeRange]: `2019-01-01, 13:00 - 2019-01-31, 14:00`
 * - [long date time with day of week][formatLongDateTimeWithDayOfWeek]: `Thursday 2019-01-31, 14:21`
 * - [long date with day of week][formatLongDateWithDayOfWeek]: `Thursday 2019-01-31`
 * - [long date with time range][formatLongDateWithTimeRange]: `2019-01-31, 14:21 - 15:21`
 * - [short date][formatShortDate]: `31 jan`
 * - [short date range][formatShortDateRange]: `1 jan - 31 jan`
 * - [short date time][formatShortDateTime]: `31 jan, 14:21`
 * - [short date time range][formatShortDateTimeRange]: `1 jan, 14:00 - 31 jan, 13:00`
 * - [short date time with day of week][formatShortDateTimeWithDayOfWeek]: `Friday 31 jan, 14:21`
 * - [short date with day of week][formatShortDateWithDayOfWeek]: `Friday 31 jan`
 * - [short date with time range][formatShortDateWithTimeRange]: `31 jan, 14:21 - 15:41`
 * - [time][formatTime]: `14:59`
 * - [time range][formatTimeRange]: `14:59 - 15:01`
 * - [time with list of days][formatTimeWithDaysList]: `14:59 - mon, wed, fri`
 */
class DateTimeFormatter @Inject constructor(
    clock: Clock,
    locale: Locale,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val shortDayOfWeekFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("E", locale)
    private val fullDayOfWeekFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE", locale)

    private val zoneAdjustedLongDateFormatter: DateTimeFormatter
    private val zoneAdjustedLongDateWithDayOfWeekFormatter: DateTimeFormatter
    private val zoneAdjustedShortDateFormatter: DateTimeFormatter
    private val zoneAdjustedShortDateWithDayOfWeekFormatter: DateTimeFormatter
    private val zoneAdjustedTimeFormatter: DateTimeFormatter

    init {
        val zoneId = clock.zone
        zoneAdjustedLongDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", locale).withZone(zoneId)
        zoneAdjustedLongDateWithDayOfWeekFormatter =
            DateTimeFormatter.ofPattern("EEEE yyyy-MM-dd", locale).withZone(zoneId)
        zoneAdjustedShortDateFormatter = DateTimeFormatter.ofPattern("d MMM", locale).withZone(zoneId)
        zoneAdjustedShortDateWithDayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE d MMM", locale).withZone(zoneId)
        zoneAdjustedTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", locale).withZone(zoneId)
    }

    /**
     * Sample: `14:59`
     */
    fun formatTime(time: TemporalAccessor): String {
        return zoneAdjustedTimeFormatter.format(time)
    }

    /**
     * Sample: `14:59 - mon, wed, fri`
     */
    fun formatTimeWithDaysList(time: TemporalAccessor, days: List<DayOfWeek>): String {
        var timeString = formatTime(time)
        if (days.isNotEmpty()) {
            val daysString = days.joinToString { shortDayOfWeekFormatter.format(it).lowercase(Locale.ROOT) }
            timeString += " - $daysString"
        }
        return timeString
    }

    /**
     * Sample: `Monday, 12:58`
     */
    fun formatDayOfWeekWithTime(dayOfWeek: DayOfWeek, time: TemporalAccessor): String {
        return "${formatDayOfWeek(dayOfWeek)}, ${formatTime(time)}"
    }

    /**
     * Sample: `Monday`
     */
    private fun formatDayOfWeek(dayOfWeek: DayOfWeek): String {
        return fullDayOfWeekFormatter.format(dayOfWeek)
    }

    /**
     * Sample: `2019-01-31`
     */
    fun formatLongDate(date: TemporalAccessor): String {
        return zoneAdjustedLongDateFormatter.format(date)
    }

    /**
     * Sample: `Friday 2019-01-31`
     */
    fun formatLongDateWithDayOfWeek(date: TemporalAccessor): String {
        return zoneAdjustedLongDateWithDayOfWeekFormatter.format(date)
    }

    /**
     * Sample: `Friday 2019-01-21, 14:21`
     */
    fun formatLongDateTimeWithDayOfWeek(date: TemporalAccessor): String {
        return "${formatLongDateWithDayOfWeek(date)}, ${formatTime(date)}"
    }

    /**
     * Sample: `31 jan`
     */
    fun formatShortDate(date: TemporalAccessor): String {
        return zoneAdjustedShortDateFormatter.format(date).lowercase(Locale.ROOT)
    }

    /**
     * Sample: `2019-01-31, 14:21`
     */
    fun formatLongDateTime(dateTime: TemporalAccessor): String {
        return "${formatLongDate(dateTime)}, ${formatTime(dateTime)}"
    }

    /**
     * Sample: `2019-01-31, 14:21 - 15:21`
     */
    fun formatLongDateWithTimeRange(from: TemporalAccessor, to: TemporalAccessor): String {
        return "${formatLongDate(from)}, ${formatTimeRange(from, to)}"
    }

    /**
     * Sample: `14:59 - 15:01`
     */
    fun formatTimeRange(from: TemporalAccessor, to: TemporalAccessor): String {
        return "${formatTime(from)} - ${formatTime(to)}"
    }

    /**
     * Sample: `2019-01-01 - 2019-01-31`
     */
    fun formatLongDateRange(dateFrom: TemporalAccessor, dateTo: TemporalAccessor): String {
        return "${formatLongDate(dateFrom)} - ${formatLongDate(dateTo)}"
    }

    /**
     * Sample: `2019-01-01, 13:00 - 2019-01-31, 14:00`
     */
    fun formatLongDateTimeRange(dateTimeFrom: TemporalAccessor, dateTimeTo: TemporalAccessor): String {
        return "${formatLongDateTime(dateTimeFrom)} - ${formatLongDateTime(dateTimeTo)}"
    }

    /**
     * Sample: `31 jan, 14:21`
     */
    fun formatShortDateTime(dateTime: TemporalAccessor): String {
        return "${formatShortDate(dateTime)}, ${formatTime(dateTime)}"
    }

    /**
     * Sample: `1 jan, 14:00 - 31 jan, 13:00`
     */
    fun formatShortDateTimeRange(dateTimeFrom: TemporalAccessor, dateTimeTo: TemporalAccessor): String {
        return "${formatShortDateTime(dateTimeFrom)} - ${formatShortDateTime(dateTimeTo)}"
    }

    /**
     * Sample: `31 jan, 14:21 - 15:41`
     */
    fun formatShortDateWithTimeRange(dateTimeFrom: TemporalAccessor, dateTimeTo: TemporalAccessor): String {
        return "${formatShortDate(dateTimeFrom)}, ${formatTimeRange(dateTimeFrom, dateTimeTo)}"
    }

    /**
     * Sample: `1 jan - 31 jan`
     */
    fun formatShortDateRange(dateFrom: TemporalAccessor, dateTo: TemporalAccessor): String {
        return "${formatShortDate(dateFrom)} - ${formatShortDate(dateTo)}"
    }

    /**
     * Sample: `Friday 31 jan`
     */
    fun formatShortDateWithDayOfWeek(date: TemporalAccessor): String {
        return zoneAdjustedShortDateWithDayOfWeekFormatter.format(date)
            .lowercase(Locale.ROOT)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }

    /**
     * Sample: `Friday 31 jan, 14:21`
     */
    fun formatShortDateTimeWithDayOfWeek(date: TemporalAccessor): String {
        return "${formatShortDateWithDayOfWeek(date)}, ${formatTime(date)}"
    }
}
