package com.abdulaziz.submissionfinal.playerdetail

import com.abdulaziz.submissionfinal.api.repository.ApiRepositoryCallback
import com.abdulaziz.submissionfinal.model.PlayerDetailResponse
import com.abdulaziz.submissionfinal.model.PlayerResponse

interface PlayerDetailView : ApiRepositoryCallback<PlayerDetailResponse> {
    fun showLoading()
    fun hideLoading()
}