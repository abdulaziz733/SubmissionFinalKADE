package com.abdulaziz.submissionfinal.api

import com.abdulaziz.submissionfinal.model.*
import com.abdulaziz.submissionfinal.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(Constants.ALL_LEAGUES)
    fun getAllLeagues(): Call<LeagueResponse>

    @GET(Constants.NEXT_EVENT)
    fun getNextEvent(@Query("id") eventId: String): Call<EventLeagueResponse>

    @GET(Constants.LAST_EVENT)
    fun getLastEvent(@Query("id") eventId: String): Call<EventLeagueResponse>

    @GET(Constants.DETAIL_EVENT)
    fun getEventDetail(@Query("id") eventId: String): Call<EventLeagueResponse>

    @GET(Constants.BADGE_TEAM)
    fun getTeamBade(@Query("t") teamName: String): Call<TeamsResponse>

    @GET(Constants.ALL_TEAM)
    fun getAllteams(@Query("l") leagueName: String): Call<TeamsResponse>

    @GET(Constants.TEAM_DETAIL)
    fun getTeamDetail(@Query("id") idTeam: String): Call<TeamsResponse>

    @GET(Constants.ALL_PLAYER)
    fun getAllPlayer(@Query("id") idTeam: String) : Call<PlayerResponse>

    @GET(Constants.PLAYER_DETAIL)
    fun getPlayerDetail(@Query("id") idPlayer: String) : Call<PlayerDetailResponse>

}