package com.abdulaziz.submissionfinal.eventleaguedetail

import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.api.repository.ApiRepositoryCallback
import com.abdulaziz.submissionfinal.model.EventLeagueResponse
import com.abdulaziz.submissionfinal.model.TeamsResponse

class EventLeagueDetailPresenter(private val eventLeagueDetailView: EventLeagueDetailView,
                                 private val apiRepository: ApiRepository) {

    fun getDataImage(teamName: String, typeTeam: Int) {
        apiRepository.getDataImage(teamName, typeTeam, object : ApiRepositoryCallback<TeamsResponse> {
            override fun onDataLoaded(data: TeamsResponse?) {
                if (typeTeam == 1) {
                    data?.let { eventLeagueDetailView.getHomeBade(data.teams[0].strTeamBadge) }
                } else if (typeTeam == 2) {
                    data?.let { eventLeagueDetailView.getAwayBade(data.teams[0].strTeamBadge) }
                }
            }

            override fun onDataErrorLoad() {
                eventLeagueDetailView.onDataErrorLoad()
            }

        })
    }

    fun getDetailEvent(eventID: String) {
        apiRepository.getDetailEvent(eventID, object : ApiRepositoryCallback<EventLeagueResponse> {
            override fun onDataLoaded(data: EventLeagueResponse?) {
                data?.let { data.eventLeagues[0].let { eventLeagueDetailView.showDetailEvent(it) } }
            }

            override fun onDataErrorLoad() {
                eventLeagueDetailView.onDataErrorLoad()
            }
        })
    }

}
