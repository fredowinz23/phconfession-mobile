package com.ph.confession.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.ph.confession.R
import com.ph.confession.adapters.HotListAdapter
import com.ph.confession.viewmodels.ConfessionViewModel
import com.ph.confession.activities.ConfessActivity
import com.ph.confession.base.api.GetConfessionAsyncTask
import com.ph.confession.base.models.ConfessionEntity

/**
 * A simple [Fragment] subclass.
 */

class HotNavFragment : Fragment() {

    private lateinit var myRecyclerView : RecyclerView
    private lateinit var progressBar : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.hot_list, container, false)
        myRecyclerView = rootView.findViewById(R.id.my_recycler_view) as RecyclerView

        val btnCompose = rootView.findViewById(R.id.btnCompose) as FloatingActionButton
        progressBar = rootView.findViewById(R.id.progressBar) as ProgressBar
        btnCompose.visibility = View.VISIBLE

        var dataList: List<ConfessionEntity> = ArrayList()
        // Set LayoutManager for RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        myRecyclerView.layoutManager = layoutManager

        val adapter = HotListAdapter(activity, ArrayList())
        myRecyclerView.adapter = adapter


        myRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recyclerView!!.layoutManager.itemCount
                if (totalItemCount == layoutManager.findLastVisibleItemPosition() + 1) {
                    try {
                        adapter.loadMore(dataList, layoutManager.findLastVisibleItemPosition())
                    }catch(e: Exception){

                    }
                }
            }
        })

        val viewModel = ViewModelProviders.of(activity).get(ConfessionViewModel::class.java)

        // Get activities in ViewModel
        viewModel.confessionList.observe(activity, Observer<List<ConfessionEntity>> {
            tickets ->
            dataList = tickets!!.reversed()
            if (tickets.isNotEmpty()) {
                adapter.addItems(dataList)
            }
            adapter.notifyDataSetChanged()
        })

        btnCompose.setOnClickListener {

            val intent = Intent(activity, ConfessActivity::class.java)
            activity.startActivity(intent)
        }

        return rootView
    }

    override fun onStart() {
        super.onStart()
        GetData().execute()
    }

    override fun onResume() {
        super.onResume()

        GetData().execute()

//            val handler = Handler()
//            handler.postDelayed({
//                progressBar.visibility = View.GONE
//            }, 3000)  //the time is in miliseconds
    }

    inner class GetData : AsyncTask<String, String, String>() {

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): String {

                getConfessionData()

            return ""
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            progressBar.visibility = View.GONE

        }
    }

    private fun getConfessionData(){
        GetConfessionAsyncTask(activity).execute()
    }
}