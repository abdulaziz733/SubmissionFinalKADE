package com.abdulaziz.submissionfinal.teamdetailplayer

import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.api.repository.ApiRepositoryCallback
import com.abdulaziz.submissionfinal.eventleague.EventLeagueView
import com.abdulaziz.submissionfinal.model.PlayerResponse

class TeamDetailPlayerPresenter(private val view: TeamDetailPlayerView,
                                private val apiRepository: ApiRepository){

    fun getAllPlayer(idTeam: String){
        view.showLoading()
        apiRepository.getAllPlayer(idTeam, object : ApiRepositoryCallback<PlayerResponse>{
            override fun onDataLoaded(data: PlayerResponse?) {
                view.onDataLoaded(data)
            }

            override fun onDataErrorLoad() {
                view.onDataErrorLoad()
            }
        })
        view.hideLoading()
    }

}