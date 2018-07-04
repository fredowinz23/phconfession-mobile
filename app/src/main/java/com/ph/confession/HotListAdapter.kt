package com.ph.confession

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ph.confession.models.ConfessionEntity

class HotListAdapter(context: Context, arrayList: List<ConfessionEntity>) : RecyclerView.Adapter<HotListAdapter.ViewHolder>() {

    /** Data set for display list */
    private var confessionList= arrayList

    private var context = context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Set view items in layout as attributes
        var title = view.findViewById<View>(R.id.title) as TextView
        var category = view.findViewById<View>(R.id.category) as TextView
        var message = view.findViewById<View>(R.id.message) as TextView
        var mediaImage = view.findViewById<View>(R.id.media_image) as ImageView

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Attach Item View to RecycleView
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.hot_list_item, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Bind data to ViewHolder ( RecycleView with ItemView)
        val categories = Functions().category()
        val categoryId = confessionList[position].category
        viewHolder.title.text = confessionList[position].title
        viewHolder.category.text = categories[categoryId!!.toInt()]
        viewHolder.message.text = confessionList[position].message


        if (categoryId == "6" || categoryId == "9") {
            viewHolder.mediaImage.setImageResource(R.drawable.bg_red)
        }
        else if (categoryId == "2" || categoryId == "7") {
            viewHolder.mediaImage.setImageResource(R.drawable.bg_orange)
        }
        else if (categoryId == "8" || categoryId == "10") {
            viewHolder.mediaImage.setImageResource(R.drawable.bg_yellow)
        }
        else if (categoryId == "1" || categoryId == "2") {
            viewHolder.mediaImage.setImageResource(R.drawable.bg_green)
        }
        else if (categoryId == "3" || categoryId == "4") {
            viewHolder.mediaImage.setImageResource(R.drawable.bg_blue)
        }
        else if (categoryId == "11" || categoryId == "12") {
            viewHolder.mediaImage.setImageResource(R.drawable.bg_indigo)
        }
        else {
            viewHolder.mediaImage.setImageResource(R.drawable.bg_violet)
        }

    }

    override fun getItemCount(): Int {
        return confessionList.size
    }

    fun addItems(dataList: List<ConfessionEntity>) {
        this.confessionList = dataList
        notifyDataSetChanged()
    }

    init {
        // Get sample data when initial adapter in LY
        confessionList = arrayList
    }
}




