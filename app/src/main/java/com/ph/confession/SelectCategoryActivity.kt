package com.ph.confession

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.ph.confession.base.Functions
import kotlinx.android.synthetic.main.category_list.*


class SelectCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.category_list)

        // Set AppBar as ToolBar of viewMain
        val toolbarMain = toolbar
        toolbarMain.title = "Select Category"
        setSupportActionBar(toolbarMain)

        // Set LayoutManager for RecyclerView
        val layoutManager = LinearLayoutManager(this)
        my_recycler_view.layoutManager = layoutManager

        // Initialize adapter as ActvpackListAdapter
        val categories = Functions().category()
        val adapter = SelectCategoryAdapter(this, categories)
        // Set adapter to this Recyclerview
        my_recycler_view.adapter = adapter

    }

    override fun onBackPressed() {
        // Do nothing to prevent seeing previous page
    }

}
