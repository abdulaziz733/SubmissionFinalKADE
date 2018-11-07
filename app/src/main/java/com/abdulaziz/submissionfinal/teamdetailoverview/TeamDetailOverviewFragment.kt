package com.abdulaziz.submissionfinal.teamdetailoverview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.abdulaziz.submissionfinal.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.swipeRefreshLayout

private const val ARG_PARAM1 = "txtOverview"

class TeamDetailOverviewFragment : Fragment() {
    private var overview: String? = null
    private lateinit var txtOverview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            overview = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return UI {
            scrollView {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    topPadding = dip(16)
                    leftPadding = dip(16)
                    rightPadding = dip(16)
                    gravity = View.TEXT_ALIGNMENT_CENTER

                    txtOverview = textView {
                        textSize = sp(6).toFloat()

                    }.lparams(width = matchParent, height = wrapContent)

                }
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtOverview.text = overview
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                TeamDetailOverviewFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
}
