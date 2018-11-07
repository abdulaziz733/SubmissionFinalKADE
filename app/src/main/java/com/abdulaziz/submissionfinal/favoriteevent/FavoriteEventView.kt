package com.abdulaziz.submissionfinal.favoriteevent

import com.abdulaziz.submissionfinal.db.FavoriteEvent

interface FavoriteEventView {
    fun showEventList(data: List<FavoriteEvent>)
}