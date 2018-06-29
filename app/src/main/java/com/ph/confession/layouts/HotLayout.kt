package com.ph.confession.layouts

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.ph.confession.MainActivity

class HotLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    // Instantiate MainActivity
    private val mainActivity = context as MainActivity

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        
        // Set LayoutManager for RecyclerView
        val layoutManager = LinearLayoutManager(context)
        this.layoutManager = layoutManager

    }

}