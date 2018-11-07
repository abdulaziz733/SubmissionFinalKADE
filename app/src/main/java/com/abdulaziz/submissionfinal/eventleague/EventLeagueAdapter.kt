package com.abdulaziz.submissionfinal.eventleague

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.model.EventLeague
import com.abdulaziz.submissionfinal.util.*
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_data_event.view.*
import java.util.ArrayList

class EventLeagueAdapter(private val context: Context,
                         private var listEvent: List<EventLeague>,
                         private val evenTppe: Int,
                         private val listener: (EventLeague) -> Unit,
                         private val listenerAddEvent: (ImageView, EventLeague) -> Unit)
    : RecyclerView.Adapter<EventLeagueAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_data_event, parent, false))

    override fun getItemCount(): Int = listEvent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listEvent[position], listener, listenerAddEvent)
        when (evenTppe) {
            1 -> holder.itemView.event_calendar.invisible()
            2 -> holder.itemView.event_calendar.visible()
        }
    }

    fun setFilter(list: List<EventLeague>) {
        listEvent = ArrayList()
        (listEvent as ArrayList<EventLeague>).addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(eventLeague: EventLeague, listener: (EventLeague) -> Unit, listenerAddEvent: (ImageView, EventLeague) -> Unit) {

            eventLeague.strDate?.let { strDate ->
                eventLeague.strTime?.let { strTime ->
                    itemView.event_date.text = toGMTFormatDate(strDate, strTime)
                    itemView.event_time.text = toGMTFormatTime(strDate, strTime)
                }
            }

            eventLeague.strHomeTeam.let { itemView.home_team_name.text = eventLeague.strHomeTeam }
            eventLeague.strAwayTeam.let { itemView.away_team_name.text = eventLeague.strAwayTeam }
            itemView.league_score.text = formatScore(eventLeague.intHomeScore, eventLeague.intAwayScore)
            itemView.setOnClickListener { listener(eventLeague) }
            itemView.event_calendar.setOnClickListener { listenerAddEvent(itemView.event_calendar, eventLeague) }

        }
    }
}