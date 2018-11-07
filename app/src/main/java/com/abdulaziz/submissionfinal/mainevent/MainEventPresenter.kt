package com.abdulaziz.submissionfinal.mainevent

import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.api.repository.ApiRepositoryCallback
import com.abdulaziz.submissionfinal.model.LeagueResponse

class MainEventPresenter(private val view: MainEventView,
                         private val apiRepository: ApiRepository) {

    fun getAllLeagues(){
        apiRepository.getAllLeagues(object : ApiRepositoryCallback<LeagueResponse>{
            override fun onDataLoaded(data: LeagueResponse?) {
                view.showAllLeagues(data?.leagues)
            }

            override fun onDataErrorLoad() {
                view.onDataErrorLoad()
            }

        })

    }

}