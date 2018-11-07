package com.abdulaziz.submissionfinal.model

import com.google.gson.annotations.SerializedName

data class EventLeagueResponse(
        @SerializedName("events")
        var eventLeagues: List<EventLeague>
)