package com.ph.confession.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ph.confession.R

/**
 * A simple [Fragment] subclass.
 */

class TrendingNavFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.trending_list, container, false)

        return rootView
    }
}