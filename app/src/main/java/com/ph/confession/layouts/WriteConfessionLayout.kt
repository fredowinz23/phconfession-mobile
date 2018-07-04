package com.ph.confession.layouts

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.Toast
import com.ph.confession.MainActivity
import com.ph.confession.SelectCategoryActivity
import com.ph.confession.models.ConfessionEntity
import com.ph.confession.models.RoomDB
import kotlinx.android.synthetic.main.activity_write_confession.view.*


class WriteConfessionLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        this.save_button.setOnClickListener {

            val intent = Intent(context, SelectCategoryActivity::class.java)
            intent.putExtra("title", this.title_input.text.toString())
            intent.putExtra("message", this.message_input.text.toString())
            context.startActivity(intent)
        }

    }
}