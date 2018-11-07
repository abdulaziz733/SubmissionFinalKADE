package com.abdulaziz.submissionfinal.api.repository

import android.util.Log
import com.abdulaziz.submissionfinal.api.ApiService
import com.abdulaziz.submissionfinal.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository {

    fun getAllLeagues(callback: ApiRepositoryCallback<LeagueResponse>) {
        ApiService.footballApi.getAllLeagues().enqueue(object : Callback<LeagueResponse> {
            override fun onResponse(call: Call<LeagueResponse>?, response: Response<LeagueResponse>?) {
                response?.let {
                    if (it.isSuccessful) {
                        callback.onDataLoaded(it.body())
                    } else {
                        callback.onDataErrorLoad()
                    }
                }
            }

            override fun onFailure(call: Call<LeagueResponse>?, t: Throwable) {
                callback.onDataErrorLoad()
            }
        })
    }

    fun getNextMatch(id: String, callback: ApiRepositoryCallback<EventLeagueResponse>) {
        ApiService.footballApi.getNextEvent(id).enqueue(object : Callback<EventLeagueResponse> {
            override fun onResponse(call: Call<EventLeagueResponse>?, response: Response<EventLeagueResponse>?) {
                response?.let {
                    if (it.isSuccessful) {
                        callback.onDataLoaded(it.body())
                    } else {
                        callback.onDataErrorLoad()
                    }
                }
            }

            override fun onFailure(call: Call<EventLeagueResponse>?, t: Throwable) {
                callback.onDataErrorLoad()
            }
        })
    }

    fun getLastMatch(id: String, callback: ApiRepositoryCallback<EventLeagueResponse>) {
        ApiService.footballApi.getLastEvent(id).enqueue(object : Callback<EventLeagueResponse> {
            override fun onResponse(call: Call<EventLeagueResponse>?, response: Response<EventLeagueResponse>?) {
                response?.let {
                    if (it.isSuccessful) {
                        callback.onDataLoaded(it.body())
                    } else {
                        callback.onDataErrorLoad()
                    }
                }
            }

            override fun onFailure(call: Call<EventLeagueResponse>?, t: Throwable) {
                callback.onDataErrorLoad()
            }
        })
    }

    fun getDataImage(teamName: String, typeTeam: Int, callback: ApiRepositoryCallback<TeamsResponse>) {
        ApiService.footballApi.getTeamBade(teamName).enqueue(object : Callback<TeamsResponse> {
            override fun onFailure(call: Call<TeamsResponse>, t: Throwable) {
                callback.onDataErrorLoad()
            }

            override fun onResponse(call: Call<TeamsResponse>, response: Response<TeamsResponse>) {
                response.let {
                    if (it.isSuccessful) {
                        callback.onDataLoaded(it.body())
                    } else {
                        callback.onDataErrorLoad()
                    }
                }
            }
        })
    }

    fun getDetailEvent(eventID: String, callback: ApiRepositoryCallback<EventLeagueResponse>) {
        ApiService.footballApi.getEventDetail(eventID).enqueue(object : Callback<EventLeagueResponse> {
            override fun onFailure(call: Call<EventLeagueResponse>, t: Throwable) {
                callback.onDataErrorLoad()
            }

            override fun onResponse(call: Call<EventLeagueResponse>, response: Response<EventLeagueResponse>) {
                response.let {
                    if (it.isSuccessful) {
                        callback.onDataLoaded(it.body())
                    } else {
                        callback.onDataErrorLoad()
                    }
                }
            }

        })
    }

    fun getAllTeams(leagueName: String, callback: ApiRepositoryCallback<TeamsResponse>) {
        ApiService.footballApi.getAllteams(leagueName).enqueue(object : Callback<TeamsResponse> {
            override fun onFailure(call: Call<TeamsResponse>, t: Throwable) {
                callback.onDataErrorLoad()
            }

            override fun onResponse(call: Call<TeamsResponse>, response: Response<TeamsResponse>) {
                response.let {
                    if (it.isSuccessful) {
                        callback.onDataLoaded(it.body())
                    } else {
                        callback.onDataErrorLoad()
                    }
                }
            }
        })
    }

    fun getTeamDetail(idteam: String, callback: ApiRepositoryCallback<TeamsResponse>) {
        ApiService.footballApi.getTeamDetail(idteam).enqueue(object : Callback<TeamsResponse> {
            override fun onFailure(call: Call<TeamsResponse>, t: Throwable) {
                callback.onDataErrorLoad()
            }

            override fun onResponse(call: Call<TeamsResponse>, response: Response<TeamsResponse>) {
                response.let {
                    if (it.isSuccessful) {
                        callback.onDataLoaded(it.body())
                    } else {
                        callback.onDataErrorLoad()
                    }
                }
            }
        })
    }

    fun getAllPlayer(idTeam: String, callback: ApiRepositoryCallback<PlayerResponse>) {
        ApiService.footballApi.getAllPlayer(idTeam).enqueue(object : Callback<PlayerResponse> {
            override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
                callback.onDataErrorLoad()
            }

            override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
                response.let {
                    if (it.isSuccessful) {
                        callback.onDataLoaded(it.body())
                    } else {
                        callback.onDataErrorLoad()
                    }
                }
            }
        })
    }

    fun getPlayerDetail(idPlayer: String, callback: ApiRepositoryCallback<PlayerDetailResponse>){
        ApiService.footballApi.getPlayerDetail(idPlayer).enqueue(object : Callback<PlayerDetailResponse>{
            override fun onFailure(call: Call<PlayerDetailResponse>, t: Throwable) {
                callback.onDataErrorLoad()
            }

            override fun onResponse(call: Call<PlayerDetailResponse>, response: Response<PlayerDetailResponse>) {
                response.let {
                    if (it.isSuccessful) {
                        callback.onDataLoaded(it.body())
                    } else {
                        callback.onDataErrorLoad()
                    }
                }
            }
        })
    }

}