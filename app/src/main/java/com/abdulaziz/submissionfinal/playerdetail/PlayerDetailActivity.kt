package com.abdulaziz.submissionfinal.playerdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.model.Player
import com.abdulaziz.submissionfinal.model.PlayerDetailResponse
import com.abdulaziz.submissionfinal.model.PlayerResponse
import com.abdulaziz.submissionfinal.util.invisible
import com.abdulaziz.submissionfinal.util.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.activity_player_detail.*
import kotlinx.android.synthetic.main.item_player.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.design.snackbar

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailView {

    private lateinit var player: Player
    private lateinit var presenter: PlayerDetailPresenter
    private lateinit var idPlayer: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        idPlayer = intent.getStringExtra("id")

        presenter = PlayerDetailPresenter(this, ApiRepository())
        presenter.getTeamDetail(idPlayer)

    }

    override fun showLoading() {
        progressbar.visible()
    }

    override fun hideLoading() {
        progressbar.invisible()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDataLoaded(data: PlayerDetailResponse?) {
        data?.let {
            data.players.let {
                player = it[0]
                supportActionBar!!.title = player.strPlayer
                player_weight.text = player.strWeight
                player_height.text = player.strHeight
                player_detail_position.text = player.strPosition
                player_description.text = player.strDescriptionEN
                Picasso.get().load(player.strThumb).into(img_player_detail)
            }
        }
    }

    override fun onDataErrorLoad() {
        snackbar(view_player_detail, "Error Load Data")
    }
}
