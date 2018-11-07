package com.abdulaziz.submissionfinal.favoriteevent

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abdulaziz.submissionfinal.eventleaguedetail.EventLeagueDetailActivity
import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.db.FavoriteEvent
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteEventEventFragment : Fragment(), AnkoComponent<Context>, FavoriteEventView {

    private var favoriteEvents: MutableList<FavoriteEvent> = mutableListOf()
    lateinit var presenter: FavoriteEventPresenter
    private lateinit var adapter: FavoriteEventAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteEventAdapter(ctx, favoriteEvents) {
            startActivity<EventLeagueDetailActivity>("idEvent" to it.eventId)
        }

        listEvent.layoutManager = LinearLayoutManager(ctx)
        listEvent.adapter = adapter

        presenter = FavoriteEventPresenter(this)
        presenter.getFavoriteData(ctx)

        swipeRefresh.onRefresh {
            favoriteEvents.clear()
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

                listEvent = recyclerView {
                    id = R.id.list_favorite
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }

    override fun showEventList(data: List<FavoriteEvent>) {
        swipeRefresh.isRefreshing = false
        favoriteEvents.clear()
        favoriteEvents.addAll(data)
        adapter.notifyDataSetChanged()
    }
}