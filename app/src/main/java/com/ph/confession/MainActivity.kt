package com.ph.confession

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.util.Log
import com.ph.confession.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val TAG = MainActivity::class.java.simpleName
    override val LAYOUT_ID = R.layout.activity_main

    val FRAG_HOT = "hoT_frag"
    val FRAG_TRENDING = "trending_frag"
    val FRAG_NEW = "new_frag"
    val FRAG_ACCOUNT = "account_frag"

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_hot -> {
                loadFragByTag(FRAG_HOT)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_trending -> {
                loadFragByTag(FRAG_TRENDING)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_new -> {
                loadFragByTag(FRAG_NEW)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                loadFragByTag(FRAG_ACCOUNT)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(navigation)

        loadFragByTag(FRAG_HOT)
    }

    private fun loadFragByTag(tag : String) {
        var frag = supportFragmentManager.findFragmentByTag(tag)
        if (frag == null) {
            Log.d(TAG, "$tag not found, creating a new one.")
            when (tag) {
                FRAG_HOT -> {
                    frag = HotFrag()
                }
                FRAG_TRENDING -> {
                    frag = TrendingFrag()
                }
                FRAG_NEW -> {
                    frag = NewFrag()
                }
                FRAG_ACCOUNT -> {
                    frag = AccountFrag()
                }
            }

        } else {
            Log.d(TAG, "$tag found.")
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_main, frag, tag)
                .commit()
    }

}
