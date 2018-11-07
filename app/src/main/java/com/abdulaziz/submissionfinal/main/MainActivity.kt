package com.abdulaziz.submissionfinal.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.abdulaziz.submissionfinal.favoriteevent.FavoriteEventEventFragment
import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.favorite.FavoriteFragment
import com.abdulaziz.submissionfinal.mainevent.MainEventFragment
import com.abdulaziz.submissionfinal.team.TeamsFragment
import com.abdulaziz.submissionfinal.util.setFrameLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(common_toolbar)

        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_matches -> {
                    setFrameLayout(supportFragmentManager, R.id.content, MainEventFragment())
                }
                R.id.menu_teams -> {
                    setFrameLayout(supportFragmentManager, R.id.content, TeamsFragment())
                }
                R.id.menu_favorite_event -> {
                    setFrameLayout(supportFragmentManager, R.id.content, FavoriteFragment())
                }
            }
            true
        }
        navigation.selectedItemId = R.id.menu_matches
    }
}
