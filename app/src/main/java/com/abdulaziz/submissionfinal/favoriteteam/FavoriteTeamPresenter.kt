package com.abdulaziz.submissionfinal.favoriteteam

import android.content.Context
import com.abdulaziz.submissionfinal.db.FavoriteTeam
import com.abdulaziz.submissionfinal.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTeamPresenter (private val view: FavoriteTeamView){

    fun getFavoriteData(ctx: Context) {
        ctx.database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorites = result.parseList(classParser<FavoriteTeam>())
            view.showTeamList(favorites)
        }
    }
}
