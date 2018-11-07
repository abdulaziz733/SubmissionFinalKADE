package com.abdulaziz.submissionfinal.favoriteteam

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.db.FavoriteTeam
import com.abdulaziz.submissionfinal.eventleaguedetail.EventLeagueDetailActivity
import com.abdulaziz.submissionfinal.favoriteevent.FavoriteEventAdapter
import com.abdulaziz.submissionfinal.favoriteevent.FavoriteEventPresenter
import com.abdulaziz.submissionfinal.teamdetail.TeamDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteTeamFragment : Fragment(), AnkoComponent<Context>, FavoriteTeamView {

    private var favoriteTeams: MutableList<FavoriteTeam> = mutableListOf()
    lateinit var presenter: FavoriteTeamPresenter
    private lateinit var adapter: FavoriteTeamAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteTeamAdapter(favoriteTeams) {
            startActivity<TeamDetailActivity>("id" to it.idTeam)
        }

        listTeam.layoutManager = LinearLayoutManager(ctx)
        listTeam.adapter = adapter

        presenter = FavoriteTeamPresenter(this)
        presenter.getFavoriteData(ctx)

        swipeRefresh.onRefresh {
            favoriteTeams.clear()
            presenter.getFavoriteData(ctx)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
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

                listTeam = recyclerView {
                    id = R.id.list_favorite
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }

    override fun showTeamList(data: List<FavoriteTeam>) {
        swipeRefresh.isRefreshing = false
        favoriteTeams.clear()
        favoriteTeams.addAll(data)
        adapter.notifyDataSetChanged()
    }

}