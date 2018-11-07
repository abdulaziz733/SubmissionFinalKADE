package com.abdulaziz.footbalapi.teams

import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.api.repository.ApiRepositoryCallback
import com.abdulaziz.submissionfinal.model.LeagueResponse
import com.abdulaziz.submissionfinal.model.TeamsResponse

class TeamPresenter(private val view: TeamView,
                    private val apiRepository: ApiRepository ) {

    fun getAllLeagues(){
        apiRepository.getAllLeagues(object : ApiRepositoryCallback<LeagueResponse> {
            override fun onDataLoaded(data: LeagueResponse?) {
               data?.let { it.leagues.let {view.showAllLeagues(it) } }
            }

            override fun onDataErrorLoad() {
                view.onDataErrorLoad()
            }
        })
    }

    fun getAllteam(leagueName: String) {
        view.showLoading()
        apiRepository.getAllTeams(leagueName, object : ApiRepositoryCallback<TeamsResponse>{
            override fun onDataLoaded(data: TeamsResponse?) {
                view.showTeamList(data?.teams!!)
            }

            override fun onDataErrorLoad() {
                view.onDataErrorLoad()
            }
        })
        view.hideLoading()
    }

}