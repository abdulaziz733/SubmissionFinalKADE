package com.abdulaziz.submissionfinal.favoriteteam

import com.abdulaziz.submissionfinal.db.FavoriteTeam

interface FavoriteTeamView {
    fun showTeamList(data: List<FavoriteTeam>)
}