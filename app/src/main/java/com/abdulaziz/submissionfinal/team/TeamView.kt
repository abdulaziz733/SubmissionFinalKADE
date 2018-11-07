package com.abdulaziz.footbalapi.teams

import com.abdulaziz.submissionfinal.model.League
import com.abdulaziz.submissionfinal.model.Team


interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
    fun showAllLeagues(data: List<League>?)
    fun onDataErrorLoad()
}