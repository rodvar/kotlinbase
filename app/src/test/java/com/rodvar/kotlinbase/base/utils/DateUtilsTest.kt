package com.rodvar.kotlinbase.base.utils

import com.rodvar.kotlinbase.base.utils.data.DateUtils
import junit.framework.Assert
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by rodvar on 8/9/17.
 */
class DateUtilsTest {

    val defaultReadableFormattedDate = "Friday, 10th November 2017"
    val defaultFormatFormattedDate = "20171110"

    @Test
    fun formatDateInAustralianEasternTime() {
        val expectetFormatedDate = defaultReadableFormattedDate
        val calendarReference = getDefaultTestCalendar()
        Assert.assertEquals(expectetFormatedDate, DateUtils.formatDate(calendarReference.time))
    }

    @Test
    fun formatNoTime() {
        val testDate = getDefaultTestCalendar().time
        Assert.assertEquals(defaultFormatFormattedDate, DateUtils.formatNoTime(testDate))
    }

    @Test
    fun startOfDateReturnsTheStartPlus1Millisecond() {
        val today = getDefaultTestCalendar().time
        val calendarReference = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendarReference.time = today
        calendar.time = DateUtils.startOfDay(today)

        Assert.assertEquals(calendarReference.get(Calendar.DATE), calendar.get(Calendar.DATE))
        assertStartingTime(calendar)
    }


    @Test
    fun startOfNextDateReturnsTheStartPlus1Millisecond() {
        val todayCalendar = getDefaultTestCalendar()
        val tomorrow = DateUtils.startOfNextDay(todayCalendar.time)
        val tomorrowCalendar = Calendar.getInstance()
        tomorrowCalendar.time = tomorrow
        val daysDifference = TimeUnit.DAYS.convert(tomorrowCalendar.timeInMillis - todayCalendar.timeInMillis, TimeUnit.MILLISECONDS)

        Assert.assertTrue(todayCalendar.before(tomorrowCalendar))
        assertStartingTime(tomorrowCalendar)
        Assert.assertTrue(0 <= daysDifference && daysDifference <= 1)
    }

    private fun assertStartingTime(calendar: Calendar) {
        Assert.assertEquals(0, calendar.get(Calendar.HOUR))
        Assert.assertEquals(0, calendar.get(Calendar.MINUTE))
        Assert.assertEquals(0, calendar.get(Calendar.SECOND))
        Assert.assertEquals(1, calendar.get(Calendar.MILLISECOND))
    }

    private fun getDefaultTestCalendar(): Calendar {
        val calendarReference = Calendar.getInstance()
        calendarReference.set(Calendar.DAY_OF_MONTH, 10)
        calendarReference.set(Calendar.MONTH, 10)
        calendarReference.set(Calendar.YEAR, 2017)
        calendarReference.set(Calendar.HOUR, 1)
        calendarReference.set(Calendar.MINUTE, 17)
        calendarReference.set(Calendar.SECOND, 54)
        calendarReference.set(Calendar.MILLISECOND, 54)
        return calendarReference
    }
}