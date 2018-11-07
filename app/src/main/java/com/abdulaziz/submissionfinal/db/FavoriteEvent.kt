package com.abdulaziz.submissionfinal.db

class FavoriteEvent(val id: Long?, val eventId: String?, val homeName:String?, val awayName: String?, val homeScore: String?, val awayScore: String?, val strDate: String?, val strTime: String?) {
    companion object {
        const val TABLE_FAVORITE_EVENT: String = "TABLE_FAVORITE_EVENT"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val HOME_NAME: String = "HOME_NAME"
        const val AWAY_NAME: String = "AWAY_NAME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val STR_DATE: String = "STR_DATE"
        const val STR_TIME: String = "STR_TIME"
    }
}