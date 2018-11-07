package com.abdulaziz.submissionfinal.mainevent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText

import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.eventleague.EventLeagueFragment
import com.abdulaziz.submissionfinal.model.League
import com.abdulaziz.submissionfinal.model.LeagueEventMessage
import com.abdulaziz.submissionfinal.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_main_event.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.ctx
import org.greenrobot.eventbus.EventBus


class MainEventFragment : Fragment(), MainEventView, SearchView.OnQueryTextListener {

    private var leagueList: MutableList<League> = mutableListOf()
    private var leagueNameList: MutableList<String> = mutableListOf()
    private lateinit var presenter: MainEventPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setHasOptionsMenu(true)
        setUpViewPager()
        main_event_tab.setupWithViewPager(main_event_viewpager)
        presenter = MainEventPresenter(this, ApiRepository())
        presenter.getAllLeagues()
        league_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                leagueList[0].idLeague.let {
                    EventBus.getDefault().post(LeagueEventMessage(1, it))
                }
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                leagueList[league_spinner.selectedItemPosition].idLeague.let {
                    EventBus.getDefault().post(LeagueEventMessage(1, it))
                }
            }

        }
    }

    override fun onDetach() {
        super.onDetach()
        this.setHasOptionsMenu(false)
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
        EventBus.getDefault().post(LeagueEventMessage(2, query))
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        EventBus.getDefault().post(LeagueEventMessage(2, newText))
        return true
    }

    private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(EventLeagueFragment.newInstance(1), "Prev")
        adapter.addFragment(EventLeagueFragment.newInstance(2), "Next")
        main_event_viewpager.adapter = adapter
    }

    override fun showAllLeagues(data: List<League>?) {
        leagueList.clear()
        leagueList.addAll(data!!)
        leagueNameList.clear()
        for (i in data.indices) {
            leagueNameList.add(leagueList[i].strLeague)
        }
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.item_spinner, leagueNameList)
        league_spinner.adapter = spinnerAdapter
    }

    override fun onDataErrorLoad() {
        snackbar(main_coor, "Error load data leagues")
    }
}