package com.abdulaziz.submissionfinal.util

import com.abdulaziz.submissionfinal.BuildConfig

class Constants {
    companion object {
        const val ALL_LEAGUES = BuildConfig.BASE_URL + "api/v1/json/1/all_leagues.php"
        const val NEXT_EVENT = BuildConfig.BASE_URL + "api/v1/json/1/eventsnextleague.php?"
        const val LAST_EVENT = BuildConfig.BASE_URL + "api/v1/json/1/eventspastleague.php?"
        const val DETAIL_EVENT = BuildConfig.BASE_URL + "api/v1/json/1/lookupevent.php"
        const val BADGE_TEAM = BuildConfig.BASE_URL + "api/v1/json/1/searchteams.php"
        const val ALL_TEAM = BuildConfig.BASE_URL + "api/v1/json/1/search_all_teams.php?"
        const val TEAM_DETAIL = BuildConfig.BASE_URL + "api/v1/json/1/lookupteam.php?"
        const val ALL_PLAYER = BuildConfig.BASE_URL + "api/v1/json/1/lookup_all_players.php?"
        const val PLAYER_DETAIL = BuildConfig.BASE_URL + "api/v1/json/1/lookupplayer.php?"
    }
}