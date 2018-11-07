package com.abdulaziz.submissionfinal.mainevent

import com.abdulaziz.submissionfinal.model.League

interface MainEventView {
    fun showAllLeagues(data: List<League>?)
    fun onDataErrorLoad()
}