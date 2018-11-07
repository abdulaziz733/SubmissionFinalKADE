package com.abdulaziz.submissionfinal.favoriteevent

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.db.FavoriteEvent
import com.abdulaziz.submissionfinal.util.formatScore
import com.abdulaziz.submissionfinal.util.formateDate
import com.abdulaziz.submissionfinal.util.toGMTFormatDate
import com.abdulaziz.submissionfinal.util.toGMTFormatTime
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_data_event.view.*

class FavoriteEventAdapter(private val context: Context,
                           private val listFavoriteEvent: List<FavoriteEvent>,
                           private val listener: (FavoriteEvent) -> Unit)
    : RecyclerView.Adapter<FavoriteEventAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            FavoriteEventAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_data_event, parent, false))


    override fun getItemCount(): Int = listFavoriteEvent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listFavoriteEvent[position], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(favoriteEvent: FavoriteEvent, listener: (FavoriteEvent) -> Unit){

            itemView.event_date.text =  toGMTFormatDate(favoriteEvent.strDate!!, favoriteEvent.strTime!!)
            itemView.event_time.text =  toGMTFormatTime(favoriteEvent.strDate, favoriteEvent.strTime)
            itemView.home_team_name.text = favoriteEvent.homeName
            itemView.away_team_name.text = favoriteEvent.awayName

            itemView.league_score.text = formatScore(favoriteEvent.homeScore, favoriteEvent.awayScore)
            itemView.setOnClickListener{ listener(favoriteEvent) }
        }
    }
}