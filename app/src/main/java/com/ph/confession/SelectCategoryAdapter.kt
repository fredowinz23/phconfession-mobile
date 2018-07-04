package com.ph.confession

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.ph.confession.models.ConfessionEntity
import com.ph.confession.models.RoomDB

class SelectCategoryAdapter(context: Context, categoryList: HashMap<Int, String>) : RecyclerView.Adapter<SelectCategoryAdapter.ViewHolder>() {

    /** Data set for display list */
    private var categoryList= categoryList
    private var activiytContext = context as SelectCategoryActivity

    val title_input = activiytContext.intent.getStringExtra("title")
    val message_input = activiytContext.intent.getStringExtra("message")

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Set view items in layout as attributes
        var category_id = view.findViewById<View>(R.id.title) as TextView
        var category_link = view.findViewById<View>(R.id.category_link) as RelativeLayout
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Attach Item View to RecycleView
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.category_list_item, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Bind data to ViewHolder ( RecycleView with ItemView)
        viewHolder.category_id.text = categoryList[position+1]

        // Jump to an activity form
        viewHolder.category_link.setOnClickListener({

            // Save Data to the database
            val conf = ConfessionEntity()
            conf.alias = "fred"
            conf.category = (position+1).toString()
            conf.title = this.title_input
            conf.message = this.message_input

            val appDatabase: RoomDB = RoomDB.getDatabase(activiytContext)
            appDatabase.confessionDAO().createOne(conf)

            Toast.makeText(activiytContext,"Thanks for sharing your confession", Toast.LENGTH_LONG).show()

            activiytContext.startActivity(Intent(activiytContext, MainActivity::class.java))
        })
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}




