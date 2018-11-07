package com.abdulaziz.footbalapi.detail

import com.abdulaziz.submissionfinal.model.Team


interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
    fun onDataErrorLoad()
}