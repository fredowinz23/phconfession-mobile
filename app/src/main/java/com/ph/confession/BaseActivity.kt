package com.ph.confession

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity() {
    abstract val TAG : String
    abstract val LAYOUT_ID : Int
    abstract val MAIN_TITLE : String
    abstract val HAS_FAB : Boolean
    abstract val HAS_BACK : Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(LAYOUT_ID)

        // Set AppBar as ToolBar of viewMain
        val toolbarMain = toolbar
        toolbarMain.title = MAIN_TITLE
        setSupportActionBar(toolbarMain)

        if (HAS_BACK){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true);
            supportActionBar!!.setDisplayShowHomeEnabled(true);
        }

        // Display fab onlick
        if (HAS_FAB){
            this.fabMethod()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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

    private fun fabMethod(){

        // Listener to record an activity
        this.fab.setOnClickListener {
            // Start home activity
            this.startActivity(Intent(this@BaseActivity, WriteConfessionActivity::class.java))
        }
    }
}