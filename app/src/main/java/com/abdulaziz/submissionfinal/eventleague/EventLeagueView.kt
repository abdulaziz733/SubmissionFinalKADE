package com.abdulaziz.submissionfinal.eventleague

import com.abdulaziz.submissionfinal.api.repository.ApiRepositoryCallback
import com.abdulaziz.submissionfinal.model.EventLeagueResponse

interface EventLeagueView : ApiRepositoryCallback<EventLeagueResponse> {
    fun showLoading()
    fun hideLoading()
}