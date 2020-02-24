package com.mindvalley.utility

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * This class is used to perform date and time related operations.
 *
 * @author SandeepD
 */
object DateTimeUtil
{
    /**
     * This function is used to convert the String formatted date into Calendar instance.
     *
     * @param inputFormat - The Input Date format
     * @param inputDate   -The input Date in String
     * @return - The Calendar instance
     */
    fun getDateFromString(inputFormat: String?, inputDate: String?): Date?
    {
        var parsed: Date? = null
        val df_input = SimpleDateFormat(inputFormat, Locale.getDefault())
        try
        {
            parsed = df_input.parse(inputDate)
        } catch(e: Exception)
        {
            Timber.e("formattedDateFromString",
                "Exception in formattedDateFromString(): " + e.message)
        }
        return parsed
    }

    /**
     * This function is used to convert the Calendar date to output Format.
     *
     * @param outputFormat - The desired ouput format of the date
     * @param inputDate    - The Date in Calendar Object
     * @return - The formatted date
     */
    fun formattedDateFromDate(inputDate: Date?, outputFormat: String?): String
    {
        var outputDate = ""
        val df_output = SimpleDateFormat(outputFormat, Locale.getDefault())
        try
        {
            outputDate = df_output.format(inputDate)
        } catch(e: Exception)
        {
            Timber.e("formattedDateFromCal",
                "Exception in formattedDateFromCalendar(): " + e.message)
        }
        return outputDate
    }
}