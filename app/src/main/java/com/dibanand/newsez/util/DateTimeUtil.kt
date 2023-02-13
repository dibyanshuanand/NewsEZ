package com.dibanand.newsez.util

object DateTimeUtil {

    //  2023-02-12T18:09:09Z -> 2023-02-12 18:09
    fun parseTimestamp(timestamp: String): String {
        val indexOfT = timestamp.indexOf('T')
        val date = timestamp.subSequence(0, indexOfT)
        val time = timestamp.subSequence(indexOfT + 1, timestamp.lastIndexOf(':'))
        return "$date $time"
    }
}