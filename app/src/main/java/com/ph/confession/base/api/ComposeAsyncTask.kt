package com.ph.confession.base.api

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.widget.Toast
import com.ph.confession.SelectCategoryActivity
import com.ph.confession.base.Api
import com.ph.confession.base.models.ConfessionEntity
import com.ph.confession.viewmodels.UserViewModel
import org.json.JSONObject
import java.io.InputStream
import java.io.UnsupportedEncodingException

class ComposeAsyncTask(val context: SelectCategoryActivity, val conf: ConfessionEntity) : AsyncTask<String, String, String>() {

//    private val dialog: ProgressDialog? = ProgressDialog(context)

    private val syncHttpHandler: SyncHttpHandler = SyncHttpHandler(context, Api().compose)

    private val preferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    private val viewModel = ViewModelProviders.of(context).get(UserViewModel::class.java)

    override fun onPreExecute() {
        // Before doInBackground
//        dialog!!.setMessage("Posting confession..")
//        dialog.show()
    }

    override fun doInBackground(vararg params: String?): String {

        var urlParameters: String ?= null
        try {
            //create the parameters and pass on as a parameter
            urlParameters = "alias=" + conf.alias +
                    "&category=" + conf.category +
                    "&title=" + conf.title +
                    "&message=" + conf.message

        } catch (e: UnsupportedEncodingException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        val inputStream: InputStream?
        val response: String

        inputStream = syncHttpHandler.postMethod(urlParameters!!)

        response = if (inputStream != null) {
            syncHttpHandler.convertStreamToString(inputStream)
        } else {
            // TODO: Incorrect username or password is always server error from response in web. Will be fixing it in the future in web side
            // Server error 500
            "Something went wrong. Try again later."
        }
        return response
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        try{
            // Parse return json response
            val jsonResult = JSONObject(result)
            // Get token response
            val success = jsonResult.optString("success")

            // Save initial Data to the database
            if (success=="true") {
                Toast.makeText(context, "You have created a ticket!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(context, "Ticket failed!", Toast.LENGTH_SHORT).show()
            }


        }catch(e: Exception){
            // This happens if error occurs
            Toast.makeText(context, "Something wrong with the connection!", Toast.LENGTH_SHORT).show()
        }

//        // Dismiss progress dialog
//        if (dialog!!.isShowing) {
//            dialog.dismiss()
//        }

//        Log.e(TAG, "Result: " + result)
    }
}