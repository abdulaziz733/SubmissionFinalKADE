package com.abdulaziz.submissionfinal.teamdetail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.abdulaziz.footbalapi.detail.TeamDetailPresenter
import com.abdulaziz.footbalapi.detail.TeamDetailView
import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.db.FavoriteEvent
import com.abdulaziz.submissionfinal.db.FavoriteTeam
import com.abdulaziz.submissionfinal.db.database
import com.abdulaziz.submissionfinal.model.Team
import com.abdulaziz.submissionfinal.teamdetailoverview.TeamDetailOverviewFragment
import com.abdulaziz.submissionfinal.teamdetailplayer.TeamDetailPlayerFragment
import com.abdulaziz.submissionfinal.util.ViewPagerAdapter
import com.abdulaziz.submissionfinal.util.invisible
import com.abdulaziz.submissionfinal.util.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var team: Team

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        setSupportActionBar(team_detail_toolbar)
        if (supportActionBar != null) supportActionBar!!.title = "Team Detail"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initCollapsingToolbar()

        team_detail_tabs.setupWithViewPager(team_detail_viewpager)

        val intent = intent
        id = intent.getStringExtra("id")

        favoriteState()
        presenter = TeamDetailPresenter(this, ApiRepository())
        presenter.getTeamDetail(id)


    }

    private fun initCollapsingToolbar() {
        team_detail_collapse_toolbar.title = " "
        team_detail_appbar.setExpanded(true)
        team_detail_appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    team_detail_collapse_toolbar.title = team.strTeam
                    img_team_badge.invisible()
                    txt_team_name.invisible()
                    txt_team_formed_year.invisible()
                    txt_team_stadium.invisible()
                    isShow = true
                } else if (isShow) {
                    team_detail_collapse_toolbar.title = " "
                    img_team_badge.visible()
                    txt_team_name.visible()
                    txt_team_formed_year.visible()
                    txt_team_stadium.visible()
                    isShow = false
                }
            }
        })
    }

    private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(TeamDetailOverviewFragment.newInstance(team.strDescriptionEN), "Overview")
        adapter.addFragment(TeamDetailPlayerFragment.newInstance(team.idTeam), "Players")
        team_detail_viewpager.adapter = adapter
    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    override fun showTeamDetail(data: List<Team>) {
        team = data[0]
        Picasso.get().load(team.strTeamBadge).into(img_team_badge)
        txt_team_name.text = team.strTeam
        txt_team_formed_year.text = team.intFormedYear
        txt_team_stadium.text = team.strStadium
        setUpViewPager()
    }

    override fun onDataErrorLoad() {
        snackbar(team_detail_appbar,"Error Load Data hehe")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        FavoriteTeam.TEAM_ID to team.idTeam,
                        FavoriteTeam.TEAM_NAME to team.strTeam,
                        FavoriteTeam.TEAM_BADGE to team.strTeamBadge)
            }

            snackbar(team_detail_maincontent, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(team_detail_maincontent, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})",
                        "id" to id)
            }
            snackbar(team_detail_maincontent, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(team_detail_maincontent, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}
