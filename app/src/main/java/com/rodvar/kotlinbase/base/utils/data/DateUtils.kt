package com.rodvar.kotlinbase.base.utils.data

import java.text.SimpleDateFormat
import java.util.*

/**
 * Utilities dealing with dates and times.
 *
 */
object DateUtils {

    val MONTHS_IN_YEAR = 12
    val dateWithDashes = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    val TZ = TimeZone.getTimeZone("Australia/Melbourne")
    private val formatter = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
    private val DEFAULT_DATE_FORMAT = "EEEE, dd% MMMM yyyy"
    private val MELBOURNE_DAY_FORMATTER = SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US)

    var sitDate: Date? = null

    init {
        MELBOURNE_DAY_FORMATTER.timeZone = TZ
    }

    /**
     * Beware, the server will treat a date of midnight as the previous day.
     *
     * @param date
     * @return Supplied date, time overwritten to 00:00.001
     */
    fun startOfDay(date: Date): Date {
        // Add one day to current time, set to 1ms after midnight
        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 1)

        return calendar.time
    }

    /**
     * Beware, the server will treat a date of midnight as the previous day.
     *
     * @param date
     * @return Supplied date, plus 1 day, time overwritten to 00:00.001
     */
    fun startOfNextDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.add(Calendar.HOUR, 24)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 1)

        return calendar.time
    }

    /**
     * Beware, the server will treat a date of midnight as the previous day.
     *
     * @param date
     * @return Supplied date, minus 1 day, time overwritten to 00:00.001
     */
    fun startOfPreviousDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.add(Calendar.HOUR, -24)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 1)

        return calendar.time
    }

    /**
     * Beware, the server will treat a date of midnight as the previous day.
     *
     * @param n number of years to roll forward
     * @return Supplied date, plus n years, time overwritten to 00:00.001
     */
    fun nYearsFromNow(n: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, n)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 1)

        return calendar.time
    }

    /**
     * Beware, the server will treat a date of midnight as the previous day.
     * Uses today date considering configured sitDate (debug)
     *
     * @param n number of months to roll backwards
     * @return Supplied date, minus n months, time overwritten to 00:00.001
     */
    fun nMonthsBeforeNow(n: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = today()
        calendar.add(Calendar.MONTH, -n)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 1)

        return calendar.time
    }

    /**
     * Beware, the server will treat a date of midnight as the previous day.
     * Uses today date considering configured sitDate (debug)
     *
     * @param n number of days to roll backwards
     * @return Supplied date, minus n days, time overwritten to 00:00.001
     */
    fun nDaysBeforeNow(n: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = today()
        calendar.add(Calendar.DATE, -n)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 1)

        return calendar.time
    }

    /**
     * Formats to the given format
     *
     * @param date to be formatted
     * @return formated string
     */
    fun format(date: Date, formatter: SimpleDateFormat): String {
        return formatter.format(date)
    }

    /**
     * Formats to YYYYMMDD
     *
     * @param date to be formatted
     * @return formated string
     */
    fun formatNoTime(date: Date): String {
        return formatter.format(date)
    }

    /**
     * @return today date considering sit conf if any
     */
    fun today(): Date {
        return if (sitDate == null) Calendar.getInstance().time else sitDate!!
    }

    /**
     * @param day number
     * @return day suffix string
     */
    fun getSuffixForDay(day: Int): String {
        if (day >= 11 && day <= 13) {
            return "th"
        }

        when (day % 10) {
            1 -> return "st"
            2 -> return "nd"
            3 -> return "rd"
            else -> return "th"
        }
    }

    /**
     * @return Formatted date always in the Australia/Melbourne TZ, regardless of TZ of device.
     */
    fun formatDate(date: Date): String {
        val calendar = Calendar.getInstance(TZ)
        calendar.time = date

        return MELBOURNE_DAY_FORMATTER.format(date).replace("%", getSuffixForDay(calendar
                .get(Calendar.DAY_OF_MONTH)))
    }
}
