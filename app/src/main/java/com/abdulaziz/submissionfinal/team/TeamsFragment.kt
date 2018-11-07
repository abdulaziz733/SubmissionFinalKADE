package com.abdulaziz.submissionfinal.team

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.abdulaziz.submissionfinal.teamdetail.TeamDetailActivity
import com.abdulaziz.footbalapi.teams.TeamAdapter
import com.abdulaziz.footbalapi.teams.TeamPresenter
import com.abdulaziz.footbalapi.teams.TeamView
import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.model.League
import com.abdulaziz.submissionfinal.model.Team
import com.abdulaziz.submissionfinal.util.invisible
import com.abdulaziz.submissionfinal.util.visible
import kotlinx.android.synthetic.main.fragment_main_event.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.util.ArrayList


class TeamsFragment : Fragment(), AnkoComponent<Context>, TeamView, SearchView.OnQueryTextListener {

    private var leagueNameList: MutableList<String> = mutableListOf()
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private var leagueName: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = TeamAdapter(teams) {
            ctx.startActivity<TeamDetailActivity>("id" to it.idTeam)
        }

        listTeam.adapter = adapter

        presenter = TeamPresenter(this, ApiRepository())
        presenter.getAllLeagues()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getAllteam(leagueName)
            }

        }
        swipeRefresh.onRefresh {
            presenter.getAllteam(leagueName)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setHasOptionsMenu(true)
    }

    override fun onDetach() {
        super.onDetach()
        this.setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.spinner
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        id = R.id.list_team
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
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.main_menu, menu)
        val item = menu!!.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(item) as SearchView
        val searchEditText = searchView.findViewById<View>(android.support.v7.appcompat.R.id.search_src_text) as EditText
        searchEditText.setTextColor(resources.getColor(R.color.md_blue_grey_400))
        searchEditText.setHintTextColor(resources.getColor(R.color.md_blue_grey_400))
        searchView.setOnQueryTextListener(this)

        MenuItemCompat.setOnActionExpandListener(item, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                return true
            }
        })

    }

    override fun onQueryTextSubmit(query: String): Boolean {
        search(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        search(newText)
        return true
    }

    fun search(newText: String) {
        val list = filter(teams, newText)
        adapter.setFilter(list)
        adapter.notifyDataSetChanged()
    }

    private fun filter(models: List<Team>, query: String): List<Team> {
        var query = query
        query = query.toLowerCase()

        val filteredModelList = ArrayList<Team>()
        var teamName: String
        for (model in models) {
            teamName = model.strTeam.toLowerCase()
            if (teamName.contains(query)) {
                filteredModelList.add(model)
            }
        }
        return filteredModelList
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showAllLeagues(data: List<League>?) {
        leagueNameList.clear()
        for (i in data!!.indices) {
            leagueNameList.add(data[i].strLeague)
        }
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.item_spinner, leagueNameList)
        spinner.adapter = spinnerAdapter
        leagueName = leagueNameList[0]
        presenter.getAllteam(leagueName)
    }

    override fun onDataErrorLoad() {
        snackbar(main_coor, "Error load data")
    }

}
