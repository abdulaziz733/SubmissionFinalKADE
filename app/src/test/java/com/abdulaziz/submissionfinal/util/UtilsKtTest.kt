package com.abdulaziz.submissionfinal.util

import org.junit.Assert.*
import org.junit.Test

class UtilsKtTest {

    @Test
    fun testFormatDate() {
        val dateToFormat = "2018-09-17"
        assertEquals("Mon, 17 Sep 2018", formateDate(dateToFormat))
    }

    @Test
    fun testFormatScore() {
        val homeScore = "3"
        val awayScore = "2"
        assertEquals("3  vs  2", formatScore(homeScore, awayScore))
    }
}