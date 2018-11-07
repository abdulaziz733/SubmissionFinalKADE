package com.abdulaziz.submissionfinal.eventleague

import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.api.repository.ApiRepositoryCallback
import com.abdulaziz.submissionfinal.model.EventLeagueResponse

class EventLeaguePresenter(private val leagueView: EventLeagueView,
                           private val apiRepository: ApiRepository) {


    fun getDataNextEvents(id: String){
        leagueView.showLoading()
        apiRepository.getNextMatch(id, object : ApiRepositoryCallback<EventLeagueResponse>{
            override fun onDataLoaded(data: EventLeagueResponse?) {
                leagueView.onDataLoaded(data)
            }

            override fun onDataErrorLoad() {
                leagueView.onDataErrorLoad()
            }
        })
        leagueView.hideLoading()
    }


    fun getDataLastEvents(id: String){
        leagueView.showLoading()
        apiRepository.getLastMatch(id, object : ApiRepositoryCallback<EventLeagueResponse>{
            override fun onDataLoaded(data: EventLeagueResponse?) {
                leagueView.onDataLoaded(data)
            }

            override fun onDataErrorLoad() {
                leagueView.onDataErrorLoad()
            }
        })
        leagueView.hideLoading()
    }

}