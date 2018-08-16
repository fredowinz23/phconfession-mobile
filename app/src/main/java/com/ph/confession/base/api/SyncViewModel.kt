package com.ph.confession.base.api

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.*
import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.ph.confession.base.Api
import com.ph.confession.base.models.*
import org.json.JSONArray
import java.io.InputStream
import java.io.UnsupportedEncodingException
import org.json.JSONObject
import java.net.URLEncoder

/**
 * ViewModel of syncpack
 *
 * TODO: This may be reused by Authpack for login and signup
 *
 */
class SyncViewModel(application: Application) : AndroidViewModel(application){

    private val preferences: SharedPreferences = application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    private val lastChange = preferences.getInt("lastChange", 0)

    /** Instantiate Database */
    private val appDatabase: RoomDB = RoomDB.getDatabase(this.getApplication())

    @SuppressLint("StaticFieldLeak")
    var context: Context? = null

    val syncHttpHandler: SyncHttpHandler = SyncHttpHandler(application, Api().getAll)

    fun getAll() {
        //Send username and password to request
        GetRecordsAsyncTask().execute("phconfession123456789sunsetcity")

    }

    @SuppressLint("StaticFieldLeak")
    inner class GetRecordsAsyncTask : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String {

            var urlParameters: String ?= null
            try {
                //create the parameters and pass on as a parameter
                urlParameters = "auth=" + URLEncoder.encode(params[0], "UTF-8") +
                                "&lastChange=$lastChange"
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

            var result = ""

            if (response.isNotEmpty()){
                // Parse return json response
                val jsonResult = JSONObject(response)

                // Get lists record from JSONObject
                val confessionList = jsonResult.optString("confession")
                val commentList = jsonResult.optString("comment")
                val notificationList = jsonResult.optString("notification")
                val relateList = jsonResult.optString("relate")

                // TODO: Delay is only for work around. Remove this if finished sync feature
                    saveConfessionToDB(confessionList)
                    saveCommentToDB(commentList)
                    saveNotificationToDB(notificationList)
                    saveRelateToDB(relateList)
            }
            else{
                result = "Something wrong with the connection!"
            }
            return result
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            if (!result.isEmpty()){
                Toast.makeText(context, "Something wrong with the connection!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveRelateToDB(relateList: String?) {
        val jsonObj = JSONArray(relateList)
        for (i in 0..(jsonObj.length() - 1)) {
            val item = jsonObj.getJSONObject(i)
            var relate = RelateEntity()
            relate.id = item.optString("Id").toInt()
            relate.cId = item.optString("cId")
            relate.alias = item.optString("alias")

            Log.e("Save-relate", relate.id.toString())
            try{
                appDatabase.relateDAO().createOne(relate)
            }catch(e: Exception){
//                appDatabase.relateDAO().updateOne(relate)
            }
        }
    }

    private fun saveNotificationToDB(notificationList: String?) {
        val jsonObj = JSONArray(notificationList)
        for (i in 0..(jsonObj.length() - 1)) {
            val item = jsonObj.getJSONObject(i)
            var notif = NotificationEntity()
            notif.id = item.optString("Id").toInt()
            notif.recepient = item.optString("recepient")
            notif.message = item.optString("message")
            notif.confessionId = item.optString("confessionId")
            notif.commentId = item.optString("commentId")
            notif.type = item.optString("type")
            notif.status = item.optString("status")
            notif.datetime = item.optString("datetime")

            Log.e("Save-notif", notif.id.toString())
            try{
                appDatabase.notificationDAO().createOne(notif)
            }catch(e: Exception){
//                appDatabase.notificationDAO().updateOne(notif)
            }
        }
    }

    private fun saveCommentToDB(commentList: String?) {
        val jsonObj = JSONArray(commentList)
        for (i in 0..(jsonObj.length() - 1)) {
            val item = jsonObj.getJSONObject(i)
            var comment = CommentEntity()
            comment.id = item.optString("Id").toInt()
            comment.cId = item.optString("cId").toInt()
            comment.comment = item.optString("oomment")
            comment.alias = item.optString("alias")
            comment.datetime = item.optString("datetime")

            Log.e("Save-comment", comment.id.toString())
            try{
                appDatabase.commentDAO().createOne(comment)
            }catch(e: Exception){
//                appDatabase.commentDAO().updateOne(comment)
            }
        }
    }

    private fun saveConfessionToDB(confessionList: String?) {
        var nextChange = 0
        val jsonObj = JSONArray(confessionList)
        for (i in 0..(jsonObj.length() - 1)) {
            val item = jsonObj.getJSONObject(i)
            var confession = ConfessionEntity()
            confession.id = item.optString("Id").toInt()
            confession.alias = item.optString("alias")
            confession.category = item.optString("category")
            confession.title = item.optString("title")
            confession.message = item.optString("message")
            confession.location = item.optString("location")
            confession.datetime = item.optString("datetime")
            confession.view = item.optString("view").toInt()
            confession.lastChange = item.optString("lastChange")

            nextChange = confession.id!!
            try{
                appDatabase.confessionDAO().createOne(confession)
                Log.e("Save", confession.id.toString())
            }catch(e: Exception){
//                appDatabase.confessionDAO().updateOne(confession)
                Log.e("ignore", confession.id.toString())
            }
        }
        val nextChangeEdit = preferences.edit()
        nextChangeEdit.putInt("lastChange", nextChange)
        nextChangeEdit.apply()
    }

}
