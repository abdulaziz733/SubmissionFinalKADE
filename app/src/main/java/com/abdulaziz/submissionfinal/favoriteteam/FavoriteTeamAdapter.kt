package com.abdulaziz.submissionfinal.favoriteteam

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.db.FavoriteEvent
import com.abdulaziz.submissionfinal.db.FavoriteTeam
import com.abdulaziz.submissionfinal.favoriteevent.FavoriteEventAdapter
import com.abdulaziz.submissionfinal.util.formatScore
import com.abdulaziz.submissionfinal.util.formateDate
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_data_event.view.*
import org.jetbrains.anko.*

class FavoriteTeamAdapter(private val listFavoriteTeam: List<FavoriteTeam>,
                          private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
             ViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))


    override fun getItemCount(): Int = listFavoriteTeam.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listFavoriteTeam[position], listener)
    }

    class TeamUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout{
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(16)
                    orientation = LinearLayout.HORIZONTAL

                    imageView {
                        id = R.id.team_badge
                    }.lparams{
                        height = dip(50)
                        width = dip(50)
                    }

                    textView {
                        id = R.id.team_name
                        textSize = 16f
                    }.lparams{
                        margin = dip(15)
                    }

                }
            }
        }

    }


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val teamBadge: ImageView = containerView.find(R.id.team_badge)
        private val teamName: TextView = containerView.find(R.id.team_name)

        fun bindItem(favoriteTeam: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
            Picasso.get().load(favoriteTeam.strTeamBadge).into(teamBadge)
            teamName.text = favoriteTeam.strTeam
            itemView.setOnClickListener { listener(favoriteTeam) }

        }

    }

}



