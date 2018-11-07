package com.abdulaziz.submissionfinal.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.abdulaziz.submissionfinal.R
import com.abdulaziz.submissionfinal.favoriteevent.FavoriteEventEventFragment
import com.abdulaziz.submissionfinal.favoriteteam.FavoriteTeamFragment
import com.abdulaziz.submissionfinal.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
        favorite_tab.setupWithViewPager(favorite_viewpager)
    }

    private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FavoriteEventEventFragment(), "MATCHES")
        adapter.addFragment(FavoriteTeamFragment(), "TEAM")
        favorite_viewpager.adapter = adapter
    }


}
