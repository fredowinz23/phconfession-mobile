package com.ph.confession

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ph.confession.models.ConfessionEntity
import kotlinx.android.synthetic.main.hot_list_item.view.*
import kotlin.collections.ArrayList

class HotListAdapter(arrayList: ArrayList<ConfessionEntity>) : RecyclerView.Adapter<HotListAdapter.ViewHolder>() {

    /** Data set for display list */
    private var confessionList= arrayList

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Set view items in layout as attributes
        var title = view.findViewById<View>(R.id.title) as TextView
        var category = view.findViewById<View>(R.id.category) as TextView

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Attach Item View to RecycleView
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.hot_list_item, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Bind data to ViewHolder ( RecycleView with ItemView)
        viewHolder.title.text = confessionList[position].title
        viewHolder.category.text = confessionList[position].category

    }

    override fun getItemCount(): Int {
        return confessionList.size
    }

    fun addItems(dataList: ArrayList<ConfessionEntity>) {
        this.confessionList = dataList
        notifyDataSetChanged()
    }

    init {
        // Get sample data when initial adapter in LY
        confessionList = arrayList
    }
}




