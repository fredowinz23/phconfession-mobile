package com.ph.confession.base.api

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.io.UnsupportedEncodingException
import com.ph.confession.base.Api
import com.ph.confession.base.models.*


class GetConfessionAsyncTask(private val context: Context) : AsyncTask<String, String, String>() {

    private val preferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    private val lastConfession = preferences.getInt("lastConfession", 0)
    private val lastComment = preferences.getInt("lastComment", 0)
    private val lastNotification = preferences.getInt("lastNotification", 0)
    private val lastRelate = preferences.getInt("lastRelate", 0)

    val syncHttpHandler: SyncHttpHandler = SyncHttpHandler(context, Api().getAll)

    /** Instantiate Database */
    private val appDatabase: RoomDB = RoomDB.getDatabase(context)

    override fun onPreExecute() {
        try {
            Toast.makeText(context, "Syncing from db...", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){

        }
    }

    override fun doInBackground(vararg params: String?): String {

        var urlParameters: String ?= null
        try {
            //create the parameters and pass on as a parameter
            urlParameters = "auth=phconfession123456789sunsetcity" +
                    "&lastConfession=$lastConfession" +
                    "&lastComment=$lastComment" +
                    "&lastNotification=$lastNotification" +
                    "&lastRelate=$lastRelate"
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

//        if (!result.isEmpty()){
//            Toast.makeText(context, "Something wrong with the connection!", Toast.LENGTH_SHORT).show()
//        }
    }


    private fun saveRelateToDB(relateList: String?) {
        var lastRelate = 0
        val jsonObj = JSONArray(relateList)
        for (i in 0..(jsonObj.length() - 1)) {
            val item = jsonObj.getJSONObject(i)
            var relate = RelateEntity()
            relate.id = item.optString("Id").toInt()
            relate.cId = item.optString("cId")
            relate.alias = item.optString("alias")

            lastRelate = relate.id!!

            Log.e("Save-relate", relate.id.toString())
            try{
                appDatabase.relateDAO().createOne(relate)
            }catch(e: Exception){
//                appDatabase.relateDAO().updateOne(relate)
            }
        }
        val nextChangeEdit = preferences.edit()
        nextChangeEdit.putInt("lastRelate", lastRelate)
        nextChangeEdit.apply()
    }

    private fun saveNotificationToDB(notificationList: String?) {
        var lastNotification = 0
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

            lastNotification = notif.id!!

            Log.e("Save-notif", notif.id.toString())
            try{
                appDatabase.notificationDAO().createOne(notif)
            }catch(e: Exception){
//                appDatabase.notificationDAO().updateOne(notif)
            }
        }
        val nextChangeEdit = preferences.edit()
        nextChangeEdit.putInt("lastNotification", lastNotification)
        nextChangeEdit.apply()
    }

    private fun saveCommentToDB(commentList: String?) {
        var lastComment = 0
        val jsonObj = JSONArray(commentList)
        for (i in 0..(jsonObj.length() - 1)) {
            val item = jsonObj.getJSONObject(i)
            var comment = CommentEntity()
            comment.id = item.optString("Id").toInt()
            comment.cId = item.optString("cId").toInt()
            comment.comment = item.optString("oomment")
            comment.alias = item.optString("alias")
            comment.datetime = item.optString("datetime")

            lastComment = comment.id!!

            Log.e("Save-comment", comment.id.toString())
            try{
                appDatabase.commentDAO().createOne(comment)
            }catch(e: Exception){
//                appDatabase.commentDAO().updateOne(comment)
            }
        }
        val nextChangeEdit = preferences.edit()
        nextChangeEdit.putInt("lastComment", lastComment)
        nextChangeEdit.apply()
    }

    private fun saveConfessionToDB(confessionList: String?) {
        var lastConfession = 0
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

            lastConfession = confession.id!!
            try{
                appDatabase.confessionDAO().createOne(confession)
                Log.e("Save", confession.id.toString())
            }catch(e: Exception){
//                appDatabase.confessionDAO().updateOne(confession)
                Log.e("ignore", confession.id.toString())
            }
        }
        val nextChangeEdit = preferences.edit()
        nextChangeEdit.putInt("lastConfession", lastConfession)
        nextChangeEdit.apply()
    }
}
