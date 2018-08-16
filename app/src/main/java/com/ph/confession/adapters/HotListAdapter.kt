package com.ph.confession.adapters

import android.support.v7.widget.RecyclerView

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.ph.confession.R
import com.ph.confession.base.Functions
import com.ph.confession.base.models.ConfessionEntity


class HotListAdapter(private var context: Context, arrayList: List<ConfessionEntity>) : RecyclerView.Adapter<HotListAdapter.ViewHolder>() {

    var list = ArrayList<ConfessionEntity>()

    /** Data set for display list */
    private var confessionList= arrayList

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Set view items in layout as attributes
        var title = view.findViewById<View>(R.id.title) as TextView
        var category = view.findViewById<View>(R.id.category) as TextView
        var message = view.findViewById<View>(R.id.message) as TextView
        var categoryColor = view.findViewById<View>(R.id.category_color) as LinearLayout

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Attach Item View to RecycleView
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.confession_item, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Bind data to ViewHolder ( RecycleView with ItemView)
        val categories = Functions().category()
        val categoryId = confessionList[position].category
        viewHolder.title.text = confessionList[position].title
        viewHolder.category.text = categories[categoryId!!.toInt()]
        viewHolder.message.text = confessionList[position].message


        when (categoryId) {
            "1" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat1))
            }
            "2" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat2))
            }
            "3" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat3))
            }
            "4" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat4))
            }
            "5" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat5))
            }
            "6" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat6))
            }
            "7" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat7))
            }
            "8" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat8))
            }
            "9" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat9))
            }
            "10" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat10))
            }
            "11" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat11))
            }
            "12" -> {
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat12))
            }
            else ->{
                viewHolder.categoryColor.setBackgroundColor(ContextCompat.getColor(context, R.color.cat1))
            }
        }

    }

    override fun getItemCount(): Int {
        return confessionList.size
    }

    fun addItems(dataList: List<ConfessionEntity>) {
        if (dataList.size>5) {
            for (i in 0 until 5) {
                list.add(dataList[i])
            }
            this.confessionList = list
            notifyDataSetChanged()
        }
    }

    fun loadMore(dataList: List<ConfessionEntity>, lastItem: Int) {
//        if (dataList.size>5) {
            for (i in lastItem until lastItem + 5) {
                list.add(dataList[i])
            }
            notifyDataSetChanged()
//        }
    }

    init {
        // Get sample data when initial adapter in LY
        confessionList = arrayList
    }
}
