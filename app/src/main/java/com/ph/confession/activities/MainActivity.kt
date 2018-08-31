package com.ph.confession.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.ph.confession.R
import com.ph.confession.adapters.NavPagerAdapter
import com.ph.confession.base.api.GetConfessionAsyncTask
import com.ph.confession.base.api.LogoutAsyncTask
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var prefs : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate")
        setContentView(R.layout.activity_main)

//        this.startService()


//        val refreshedToken = FirebaseInstanceId.getInstance().token

        // Check if loggedin
        prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val loggedInUser = prefs!!.getString("alias", null)

        if (loggedInUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            this.startActivity(intent)
            finish()
        }

        // Set AppBar as ToolBar of viewMain
        val toolbarMain = toolbar
        toolbarMain.title = "PH Confession"
        setSupportActionBar(toolbarMain)

        val fragmentAdapter = NavPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)
    }

    override fun onResume() {
        super.onResume()
        GetConfessionAsyncTask(this).execute()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchView: SearchView = menu.findItem(R.id.menu_search).actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
//                loadQuery("%" + query + "%")
                Toast.makeText(this@MainActivity,query, Toast.LENGTH_SHORT).show()

                val searchPrefs = prefs!!.edit()
                searchPrefs.putString("search", query)
                searchPrefs.apply()

                val intent = Intent(this@MainActivity, MainActivity::class.java)
                this@MainActivity.startActivity(intent)
                this@MainActivity.overridePendingTransition(0, 0)

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {

//                val intent = Intent(this, SettingsActivity::class.java)
//                this.startActivity(intent)

                return true
            }
            R.id.action_logout -> {

                val preferences: SharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

                // clear preferences
                preferences.edit().clear().apply()

                // Send username and password to request
                LogoutAsyncTask(this).execute()

                return true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    fun startService() {
//        startService(Intent(this, NotificationService::class.java))
//    }
//
//    // Method to stop the service
//    fun stopService(view: View) {
//        stopService(Intent(baseContext, NotificationService::class.java))
//    }

}
