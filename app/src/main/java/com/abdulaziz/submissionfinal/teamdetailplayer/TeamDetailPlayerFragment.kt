package com.abdulaziz.submissionfinal.teamdetailplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.model.Player
import com.abdulaziz.submissionfinal.model.PlayerResponse
import com.abdulaziz.submissionfinal.playerdetail.PlayerDetailActivity
import com.abdulaziz.submissionfinal.util.invisible
import com.abdulaziz.submissionfinal.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

private const val ARG_PARAM1 = "idTeam"

class TeamDetailPlayerFragment : Fragment(), TeamDetailPlayerView {

    private var idteam: String? = null
    private var listPlayer: MutableList<Player> = mutableListOf()

    private lateinit var rvListPlayer: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: TeamDetailPlayerPresenter
    private lateinit var adapter: TeamDetailPlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idteam = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return UI {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        rvListPlayer = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TeamDetailPlayerAdapter(ctx, listPlayer) {
            startActivity<PlayerDetailActivity>("id" to it.idPlayer)
        }

        rvListPlayer.adapter = adapter

        presenter = TeamDetailPlayerPresenter(this, ApiRepository())
        presenter.getAllPlayer(idteam!!)
        swipeRefresh.onRefresh {
            presenter.getAllPlayer(idteam!!)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun onDataLoaded(data: PlayerResponse?) {
        swipeRefresh.isRefreshing = false
        listPlayer.clear()
        data?.let { data.player.let { listPlayer.addAll(it) } }
        adapter.notifyDataSetChanged()
    }

    override fun onDataErrorLoad() {
        snackbar(swipeRefresh, "Error Load Data")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                TeamDetailPlayerFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
}
