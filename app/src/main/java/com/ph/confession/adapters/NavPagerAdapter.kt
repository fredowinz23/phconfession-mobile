package com.ph.confession.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ph.confession.fragments.HotNavFragment
import com.ph.confession.fragments.TrendingNavFragment

class NavPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HotNavFragment()
            }
            1 -> {
                TrendingNavFragment()
            }
            2 -> {
                TrendingNavFragment()
            }
            else -> {
                return TrendingNavFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Hot"
            1 -> "Trending"
            2 -> "New"
            else -> {
                return "Account"
            }
        }
    }

}