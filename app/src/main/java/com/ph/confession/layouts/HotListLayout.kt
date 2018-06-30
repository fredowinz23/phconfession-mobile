package com.ph.confession.layouts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.ph.confession.ConfessionViewModel
import com.ph.confession.HotListAdapter
import com.ph.confession.MainActivity
import com.ph.confession.models.ConfessionEntity


class HotListLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    // Instantiate MainActivity
    private val mainActivity = context as MainActivity

    /** Define viewModelProviders */
    private var viewModel = ViewModelProviders.of(mainActivity).get(ConfessionViewModel::class.java)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // Set LayoutManager for RecyclerView
        val layoutManager = LinearLayoutManager(context)
        this.layoutManager = layoutManager

        // Initialize adapter as ActvpackListAdapter
        //val dataList = arrayListOf("Title one", "title 2", "Title 3","Title one", "title 2", "Title 3")
        val adapter = HotListAdapter(ArrayList())
        // Set adapter to this Recyclerview
        this.adapter = adapter

        // Get activities in ViewModel
        viewModel.confessionList.observe(mainActivity, Observer<List<ConfessionEntity>> {
            misnpack -> adapter.addItems((misnpack as ArrayList<ConfessionEntity>?)!!)
        })
    }
}