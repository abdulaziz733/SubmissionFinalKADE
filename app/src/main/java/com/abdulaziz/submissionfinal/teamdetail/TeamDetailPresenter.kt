package com.abdulaziz.footbalapi.detail

import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.api.repository.ApiRepositoryCallback
import com.abdulaziz.submissionfinal.model.TeamsResponse

class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepository) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()
        apiRepository.getTeamDetail(teamId, object : ApiRepositoryCallback<TeamsResponse> {
            override fun onDataLoaded(data: TeamsResponse?) {
                data?.let { view.showTeamDetail(data.teams) }
            }

            override fun onDataErrorLoad() {
                view.onDataErrorLoad()
            }
        })
        view.hideLoading()
    }
}