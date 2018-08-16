package com.ph.confession.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ph.confession.R
import com.ph.confession.SelectCategoryActivity
import com.ph.confession.viewmodels.ConfessionViewModel
import kotlinx.android.synthetic.main.activity_confess_here.*

class ConfessActivity : AppCompatActivity() {

    private var loggedinUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        this.loggedinUser = prefs.getString("alias", null)

        setContentView(R.layout.activity_confess_here)

        // Set AppBar as ToolBar of viewMain
        val toolbarMain = toolbar
        toolbarMain.title = "Confess here"
        setSupportActionBar(toolbarMain)

        // Back icon
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        send_button.setOnClickListener {

//            // Save Data to the database
//            val ticket = TicketEntity()
//            ticket.student = loggedinUser
//            ticket.originalText = this.etText.text.toString()
//            ticket.editedText = this.etText.text.toString()
//            ticket.status = status.SENT
//
//            viewModel.saveToDB(this, ticket)
//
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)

            val intent = Intent(this, SelectCategoryActivity::class.java)
            intent.putExtra("alias", loggedinUser)
            intent.putExtra("title", this.title_input.text.toString())
            intent.putExtra("message", this.message_input.text.toString())
            this.startActivity(intent)

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()

//        val viewModel = ViewModelProviders.of(this).get(ConfessionViewModel::class.java)
//
//        // Save Data to the database
//        val ticket = TicketEntity()
//
//        ticket.student = this.loggedinUser
//        ticket.originalText = this.etText.text.toString()
//        ticket.editedText = this.etText.text.toString()
//        ticket.status = status.DRAFT
//
//        viewModel.saveToDB(this, ticket)

    }

}
