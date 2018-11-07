package com.abdulaziz.submissionfinal.favoriteevent

import android.content.Context
import com.abdulaziz.submissionfinal.db.FavoriteEvent
import com.abdulaziz.submissionfinal.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteEventPresenter(private val favoriteEventView: FavoriteEventView) {

    fun getFavoriteData(ctx: Context) {
        ctx.database.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE_EVENT)
            val favorites = result.parseList(classParser<FavoriteEvent>())
            favoriteEventView.showEventList(favorites)
        }
    }
}