package com.abdulaziz.submissionfinal.eventleaguedetail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.db.FavoriteEvent
import com.abdulaziz.submissionfinal.db.database
import com.abdulaziz.submissionfinal.model.EventLeague
import com.abdulaziz.submissionfinal.util.formatScore
import com.abdulaziz.submissionfinal.util.formateDate
import com.abdulaziz.submissionfinal.util.toGMTFormatDate
import com.abdulaziz.submissionfinal.util.toGMTFormatTime
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_league_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class EventLeagueDetailActivity : AppCompatActivity(), EventLeagueDetailView {

    var idEvent: String = ""
    lateinit var presenter: EventLeagueDetailPresenter
    lateinit var eventLeague: EventLeague
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_league_detail)

        idEvent = intent.getStringExtra("idEvent")

        presenter = EventLeagueDetailPresenter(this, ApiRepository())
        presenter.getDetailEvent(idEvent)
        favoriteState()
    }

    override fun getHomeBade(url: String) {
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(img_home_badge_team)
    }

    override fun getAwayBade(url: String) {
        Picasso.get().load(url).placeholder(R.drawable.placeholder).into(img_away_badge_team)
    }

    override fun showDetailEvent(eventLeague: EventLeague) {
        this.eventLeague = eventLeague
        event_date.text = toGMTFormatDate(eventLeague.strDate, eventLeague.strTime)
        event_time.text = toGMTFormatTime(eventLeague.strDate, eventLeague.strTime)
        league_score.text = formatScore(eventLeague.intHomeScore, eventLeague.intAwayScore)

        home_team_name.text = eventLeague.strHomeTeam
        away_team_name.text = eventLeague.strAwayTeam

        home_goals.text = eventLeague.strHomeGoalDetails as String?
        away_goals.text = eventLeague.strAwayGoalDetails as String?

        home_shot.text = eventLeague.intHomeShots as String?
        away_shot.text = eventLeague.intAwayShots as String?

        home_goal_keeper.text = eventLeague.strHomeLineupGoalkeeper as String?
        away_goal_keeper.text = eventLeague.strAwayLineupGoalkeeper as String?

        home_defense.text = eventLeague.strHomeLineupDefense as String?
        away_defense.text = eventLeague.strAwayLineupDefense as String?

        home_midfield.text = eventLeague.strHomeLineupMidfield as String?
        away_midfield.text = eventLeague.strAwayLineupMidfield as String?

        home_forward.text = eventLeague.strHomeLineupForward as String?
        away_forward.text = eventLeague.strAwayLineupForward as String?

        home_substitutes.text = eventLeague.strHomeLineupSubstitutes as String?
        away_substitutes.text = eventLeague.strAwayLineupSubstitutes as String?

        presenter.getDataImage(eventLeague.strHomeTeam, 1)
        presenter.getDataImage(eventLeague.strAwayTeam, 2)
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteEvent.TABLE_FAVORITE_EVENT,
                        FavoriteEvent.EVENT_ID to eventLeague.idEvent,
                        FavoriteEvent.HOME_NAME to eventLeague.strHomeTeam,
                        FavoriteEvent.AWAY_NAME to eventLeague.strAwayTeam,
                        FavoriteEvent.HOME_SCORE to eventLeague.intHomeScore,
                        FavoriteEvent.AWAY_SCORE to eventLeague.intAwayScore,
                        FavoriteEvent.STR_DATE to eventLeague.strDate,
                        FavoriteEvent.STR_TIME to eventLeague.strTime)
            }

            snackbar(detail_view, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(detail_view, e.localizedMessage).show()
        }
    }

    override fun onDataErrorLoad() {
        snackbar(detail_view, "Error load data")
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
                if (isFavorite) removeFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE_EVENT)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to idEvent)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun removeFavorite() {
        try {
            database.use {
                delete(FavoriteEvent.TABLE_FAVORITE_EVENT, "(EVENT_ID = {id})",
                        "id" to idEvent)
            }
            snackbar(detail_view, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(detail_view, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

}
