package com.ph.confession.base.api

import android.annotation.SuppressLint
//import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import com.ph.confession.activities.MainActivity
import com.ph.confession.base.models.RoomDB

class LogoutAsyncTask(context: Context) : AsyncTask<Void, Void, String>() {

//    val dialog: ProgressDialog? = ProgressDialog(context)
    @SuppressLint("StaticFieldLeak")
    val context = context

    override fun doInBackground(vararg params: Void?): String? {

//        val appDatabase: RoomDB = RoomDB.getDatabase(context)
//        appDatabase.ticketDAO().deleteAll()
//        appDatabase.userDAO().deleteAll()

        return "true"
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // Before doInBackground
//        dialog!!.setMessage("Logging out..")
//        dialog.show()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        // Dismiss progress dialog
//        if (dialog!!.isShowing) {
//            dialog.dismiss()
//        }
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}