package com.ph.confession

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.ph.confession.models.RoomDB
import com.ph.confession.models.ConfessionEntity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity() {
    abstract val TAG : String
    abstract val LAYOUT_ID : Int
    abstract val MAIN_TITLE : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(LAYOUT_ID)

        // Set AppBar as ToolBar of viewMain
        val toolbarMain = toolbar
        toolbarMain.title = MAIN_TITLE
        setSupportActionBar(toolbarMain)

        // Listener to record an activity
        this.fab.setOnClickListener {
            Toast.makeText(this, "You added one item", Toast.LENGTH_LONG).show()
            // This is for testing only
            val conf = ConfessionEntity()
            conf.alias = "fred"
            conf.category = "1"
            conf.title = "Test title"
            conf.message = "Hi how are you doing today?"

            val appDatabase: RoomDB = RoomDB.getDatabase(this)
            appDatabase.confessionDAO().createOne(conf)
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}