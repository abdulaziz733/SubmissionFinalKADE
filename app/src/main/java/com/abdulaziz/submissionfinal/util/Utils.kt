package com.abdulaziz.submissionfinal.util

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


val dateFormat = SimpleDateFormat("yyyy-MM-dd")
lateinit var date: Date
var score: String = ""


fun setFrameLayout(fragmentManager: FragmentManager, resId: Int, targetFragment: Fragment) {
    fragmentManager
            .beginTransaction()
            .replace(resId, targetFragment, targetFragment::class.java.simpleName)
            .commit()
}

@SuppressLint("SimpleDateFormat")
fun formateDate(dateToFormat: String): String {
    date = dateFormat.parse(dateToFormat + "")
    return SimpleDateFormat("EE, dd MMM yyyy").format(date)
}

@SuppressLint("SimpleDateFormat")
fun toGMTFormatDate(date: String?, time: String?): String? {
    val formatter = SimpleDateFormat("dd/MM/yy HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateTime = "$date $time"
    return SimpleDateFormat("EE, dd MMM yyyy").format(formatter.parse(dateTime))

}

@SuppressLint("SimpleDateFormat")
fun toGMTFormatTime(date: String?, time: String?): String? {
    val formatter = SimpleDateFormat("dd/MM/yy HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateTime = "$date $time"
    return SimpleDateFormat("HH:mm").format(formatter.parse(dateTime))
}

@SuppressLint("SimpleDateFormat")
fun toGMTFormat(date: String, time: String): Date? {
    val formatter = SimpleDateFormat("dd/MM/yy HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateTime = "$date $time"
    return formatter.parse(dateTime)
}

fun formatScore(homeScore: Any?, awayScore: Any?): String {
    score = ""
    if (homeScore != null) {
        score += homeScore.toString()
    } else {
        score += " "
    }
    score += "  vs  "
    if (awayScore != null) {
        score += awayScore.toString()
    } else {
        score += " "
    }
    return score
}




