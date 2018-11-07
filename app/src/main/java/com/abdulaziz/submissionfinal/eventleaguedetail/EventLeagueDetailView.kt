package com.abdulaziz.submissionfinal.eventleaguedetail

import com.abdulaziz.submissionfinal.model.EventLeague

interface EventLeagueDetailView {
    fun getHomeBade(url: String)
    fun getAwayBade(url: String)
    fun showDetailEvent(eventLeague: EventLeague)
    fun onDataErrorLoad()

}