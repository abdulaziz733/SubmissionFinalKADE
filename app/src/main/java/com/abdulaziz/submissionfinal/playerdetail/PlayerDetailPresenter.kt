package com.abdulaziz.submissionfinal.playerdetail

import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.api.repository.ApiRepositoryCallback
import com.abdulaziz.submissionfinal.model.PlayerDetailResponse
import com.abdulaziz.submissionfinal.model.PlayerResponse

class PlayerDetailPresenter(private val view: PlayerDetailView,
                            private val apiRepository: ApiRepository) {

    fun getTeamDetail(idPlayer: String) {
        view.showLoading()
        apiRepository.getPlayerDetail(idPlayer, object : ApiRepositoryCallback<PlayerDetailResponse> {
            override fun onDataLoaded(data: PlayerDetailResponse?) {
                view.onDataLoaded(data)
            }

            override fun onDataErrorLoad() {
                view.onDataErrorLoad()
            }

        })
        view.hideLoading()
    }

}