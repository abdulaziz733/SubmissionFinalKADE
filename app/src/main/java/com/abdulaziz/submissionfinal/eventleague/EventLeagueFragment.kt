package com.abdulaziz.submissionfinal.eventleague

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.abdulaziz.submissionfinal.eventleaguedetail.EventLeagueDetailActivity

import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.model.EventLeague
import com.abdulaziz.submissionfinal.model.EventLeagueResponse
import com.abdulaziz.submissionfinal.util.invisible
import com.abdulaziz.submissionfinal.util.visible
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*
import com.abdulaziz.submissionfinal.model.LeagueEventMessage
import com.abdulaziz.submissionfinal.util.toGMTFormat
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.ArrayList


private const val EVENT_TYPE = "eventTpe"

class EventLeagueFragment : Fragment(), EventLeagueView {

    var evenTppe: Int = 0

    lateinit var leaguePresenter: EventLeaguePresenter
    private lateinit var rvListEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueAdapter: EventLeagueAdapter
    private var eventLeagues: MutableList<EventLeague> = mutableListOf()
    private var idLeague = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            evenTppe = it.getInt(EVENT_TYPE, 0)
        }
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return UI {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
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

                        rvListEvent = recyclerView {
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
        leagueAdapter = EventLeagueAdapter(ctx, eventLeagues, evenTppe!!, {
            startActivity<EventLeagueDetailActivity>("idEvent" to it.idEvent)
        }, { imageView: ImageView, eventLeague: EventLeague ->
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, toGMTFormat(eventLeague.strDate, eventLeague.strTime)?.time)
            intent.putExtra("title", eventLeague.strHomeTeam + " vs " + eventLeague.strAwayTeam)
            ctx.startActivity(intent)
        })

        rvListEvent.adapter = leagueAdapter

        leaguePresenter = EventLeaguePresenter(this, ApiRepository())

        swipeRefresh.onRefresh {
            fetchData(idLeague, evenTppe!!)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: LeagueEventMessage) {
        when (event.eventID) {
            1 -> {
                idLeague = event.message
                fetchData(idLeague, evenTppe)
            }
            2 -> {
                search(event.message)
            }
        }
    }

    fun search(newText: String) {
        val list = filter(eventLeagues, newText)
        leagueAdapter.setFilter(list)
        leagueAdapter.notifyDataSetChanged()
    }

    private fun filter(models: List<EventLeague>, query: String): List<EventLeague> {
        var query = query
        query = query.toLowerCase()

        val filteredModelList = ArrayList<EventLeague>()
        var homeTeam: String
        var awayTeam: String
        for (model in models) {
            homeTeam = model.strHomeTeam.toLowerCase()
            awayTeam = model.strAwayTeam.toLowerCase()
            if (homeTeam.contains(query) || awayTeam.contains(query)) {
                filteredModelList.add(model)
            }
        }
        return filteredModelList
    }


    private fun fetchData(leagueId: String, eventType: Int) {
        when (eventType) {
            1 -> leaguePresenter.getDataLastEvents(leagueId)
            2 -> leaguePresenter.getDataNextEvents(leagueId)
            else -> {
                leaguePresenter.getDataNextEvents(leagueId)
            }
        }
    }

    override fun onDataLoaded(data: EventLeagueResponse?) {
        swipeRefresh.isRefreshing = false
        eventLeagues.clear()
        data?.eventLeagues.let { it?.let { eventLeagues.addAll(it) } }
        leagueAdapter.notifyDataSetChanged()
    }

    override fun onDataErrorLoad() {
        snackbar(swipeRefresh, "Failed Load Data")
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
                EventLeagueFragment().apply {
                    arguments = Bundle().apply {
                        putInt(EVENT_TYPE, param1)
                    }
                }
    }
}