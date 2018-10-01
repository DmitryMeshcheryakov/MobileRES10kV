package com.graime.streetvoice.common.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val PATTERN_FULL_DATE_WITH_TIME = "dd.MM.yyyy, HH:mm"
    const val PATTERN_DATE = "dd.MM.yyyy"
    const val PATTERN_TIME = "HH:mm"
    const val PATTERN_EVENT_DATE = "EEE MMM d yyyy\nhh:mm"

    const val PATTERN_DATE_REQ = "yyyy-MM-dd"

    private const val min = 1000L * 60
    private const val hou = 1000L * 60 * 60
    private const val day = 1000L * 60 * 60 * 24
    private const val wee = 1000L * 60 * 60 * 24 * 7
    private const val mon = 1000L * 60 * 60 * 24 * 30

    fun parseDate(date: Date, pattern: String, locale: Locale = Locale.getDefault()): String {
        val dateFormat = SimpleDateFormat(pattern, locale)
        return dateFormat.format(date)
    }

    fun getDate(date: String, pattern: String): Date {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return try {
            dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            Date()
        }
    }

    fun getFullMonth(date: Date, locale: Locale = Locale.getDefault()): String =
            parseDate(date, "MMMMM", locale)

    fun getFullDayOfWeek(date: Date, locale: Locale = Locale.getDefault()): String =
            parseDate(date, "EEEEE", locale)

    fun getShortMonth(date: Date, locale: Locale = Locale.getDefault()): String =
            parseDate(date, "MMM", locale)

    fun getShortDayOfWeek(date: Date, locale: Locale = Locale.getDefault()): String =
            parseDate(date, "EEE", locale)

    fun getDay(date: Date): Int = Integer.parseInt(parseDate(date, "dd"))

    fun getMonth(date: Date): Int = Integer.parseInt(parseDate(date, "MM"))

    fun getYear(date: Date): Int = Integer.parseInt(parseDate(date, "yyyy"))

    fun getHours(date: Date): Int = Integer.parseInt(parseDate(date, "HH"))

    fun getMinutes(date: Date): Int = Integer.parseInt(parseDate(date, "mm"))

    fun getSeconds(date: Date): Int = Integer.parseInt(parseDate(date, "ss"))

    fun getDifferenceType(date1: Date, date2: Date): Int {
        var dif = date1.time - date2.time
        if (dif < 0)
            dif = -dif
        return when {
            (dif < hou) -> Calendar.MINUTE
            (dif in hou..day) -> Calendar.HOUR
            (dif in day..wee) -> Calendar.DAY_OF_WEEK
            (dif in wee..mon) -> Calendar.WEEK_OF_MONTH
            (dif in mon..(mon * 12)) -> Calendar.MONTH
            else -> Calendar.YEAR
        }
    }

    fun getDifferenceInMunutes(date1: Date, date2: Date) : Int {
        var dif = date1.time - date2.time
        if (dif < 0)
            dif = -dif

        var result = (dif / min).toInt()
        if (result == 0) result++
        return result
    }

    fun getDifferenceInHours(date1: Date, date2: Date) : Int {
        var dif = date1.time - date2.time
        if (dif < 0)
            dif = -dif

        var result = (dif / hou).toInt()
        if (result == 0) result++
        return result
    }

    fun getDifferenceInDays(date1: Date, date2: Date) : Int {
        var dif = date1.time - date2.time
        if (dif < 0)
            dif = -dif

        var result = (dif / day).toInt()
        if (result == 0) result++
        return result
    }

    fun getDifferenceInWeek(date1: Date, date2: Date) : Int {
        var dif = date1.time - date2.time
        if (dif < 0)
            dif = -dif

        var result = (dif / wee).toInt()
        if (result == 0) result++
        return result
    }

    fun getDifferenceInMonths(date1: Date, date2: Date) : Int {
        var dif = date1.time - date2.time
        if (dif < 0)
            dif = -dif

        var result = (dif / mon).toInt()
        if (result == 0) result++
        return result
    }

    fun getDifferenceInYears(date1: Date, date2: Date) : Int {
        var dif = date1.time - date2.time
        if (dif < 0)
            dif = -dif

        var result = (dif / (mon * 12)).toInt()
        if (result == 0) result++
        return result
    }
}