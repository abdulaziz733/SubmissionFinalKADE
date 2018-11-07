package com.abdulaziz.submissionfinal.teamdetailplayer

import com.abdulaziz.submissionfinal.api.repository.ApiRepositoryCallback
import com.abdulaziz.submissionfinal.model.EventLeagueResponse
import com.abdulaziz.submissionfinal.model.PlayerResponse

interface TeamDetailPlayerView : ApiRepositoryCallback<PlayerResponse> {
    fun showLoading()
    fun hideLoading()
}