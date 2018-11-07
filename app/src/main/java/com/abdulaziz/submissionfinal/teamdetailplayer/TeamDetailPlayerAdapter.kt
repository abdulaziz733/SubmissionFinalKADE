package com.abdulaziz.submissionfinal.teamdetailplayer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.model.Player
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_player.view.*

class TeamDetailPlayerAdapter(private val context: Context,
                              private var listPlayer: List<Player>,
                              private val listener: (Player) -> Unit)
 : RecyclerView.Adapter<TeamDetailPlayerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_player, parent, false))

    override fun getItemCount(): Int = listPlayer.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listPlayer[position], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(player: Player, listener: (Player) -> Unit){

            itemView.player_name.text = player.strPlayer
            itemView.player_position.text = player.strPosition
            Picasso.get().load(player.strThumb).into(itemView.img_player)

            itemView.setOnClickListener{ listener(player) }

        }

    }
}
