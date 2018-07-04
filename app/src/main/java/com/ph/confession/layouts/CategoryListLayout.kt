package com.ph.confession.layouts

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.ph.confession.ConfessionViewModel
import com.ph.confession.Functions
import com.ph.confession.SelectCategoryActivity
import com.ph.confession.SelectCategoryAdapter


class CategoryListLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    // Instantiate MainActivity
    private val activiytContext = context as SelectCategoryActivity

    /** Define viewModelProviders */
    private var viewModel = ViewModelProviders.of(activiytContext).get(ConfessionViewModel::class.java)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // Set LayoutManager for RecyclerView
        val layoutManager = LinearLayoutManager(context)
        this.layoutManager = layoutManager

        // Initialize adapter as ActvpackListAdapter
        val categories = Functions().category()
        val adapter = SelectCategoryAdapter(activiytContext, categories)
        // Set adapter to this Recyclerview
        this.adapter = adapter
    }
}