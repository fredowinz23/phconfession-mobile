package com.ph.confession.sync

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.*
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.widget.Toast
import com.actinbox.mobibox.syncpack.SyncHttpHandler
import com.ph.confession.MainActivity
import com.ph.confession.models.*
import org.json.JSONArray
import java.io.InputStream
import java.io.UnsupportedEncodingException
import org.json.JSONObject

/**
 * ViewModel of syncpack
 *
 * TODO: This may be reused by Authpack for login and signup
 *
 */
class SyncViewModel(application: Application) : AndroidViewModel(application){
    @SuppressLint("StaticFieldLeak")
    /** Initial of context defined in SyncpackInitialLY */
    var context: Context? = null
    /** Instantiate Database */
    private val appDatabase: RoomDB = RoomDB.getDatabase(this.getApplication())
    /** Local url of actinbox API */
    val url = "https://phconfession.com/mobile/"
//    val url = "http://10.0.2.2/phconfession/mobile/"
    /** Initialize AuthpackHttpHandler for http request */
    val syncHttpHandler: SyncHttpHandler = SyncHttpHandler(application, url)


    fun getRecords() {
        //Send username and password to request
        GetRecordsAsyncTask().execute()

    }


    inner class GetRecordsAsyncTask : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String {
            var urlParameters: String ?= null
            try {
                //create the parameters and pass on as a parameter
                urlParameters = "auth=phconfession123456789sunsetcity"
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

                // Get lists record from JSONObject
                val confessionList = jsonResult.optString("confession")
                val commentList = jsonResult.optString("comment")
                val notificationList = jsonResult.optString("notification")
                val relateList = jsonResult.optString("relate")
                val userList = jsonResult.optString("user")

                // Delete records of missions, tasks and activities locally
//                deleteLocalRecords()
                // TODO: Delay is only for work around. Remove this if finished sync feature
                Handler().postDelayed({
                    saveConfessionToDB(confessionList)
                    saveCommentToDB(commentList)
                    saveNotificationToDB(notificationList)
                    saveRelateToDB(relateList)
                    saveUserToDB(userList)
                }, 1500)

                Toast.makeText((context as MainActivity), "Your data is up to date!", Toast.LENGTH_SHORT).show()

            }catch(e: Exception){
                // This happens if error occurs
                Toast.makeText((context as MainActivity), "Something went wrong here!", Toast.LENGTH_SHORT).show()
            }

            (context as MainActivity).startActivity((context as MainActivity).intent)
            // Close InitialActivity
            (context as MainActivity).finish()
        }
    }

    private fun deleteLocalRecords() {
        appDatabase.commentDAO().readAll
                .observe(context as MainActivity, Observer<List<CommentEntity>> {
                    commentList ->
                    for (comment in commentList as List<CommentEntity>){
                        appDatabase.commentDAO().deleteOne(comment)
                    }
                })

        appDatabase.confessionDAO().readAll
                .observe(context as MainActivity, Observer<List<ConfessionEntity>> {
                    confessionList ->
                    for (confession in confessionList as List<ConfessionEntity>){
                        appDatabase.confessionDAO().deleteOne(confession)
                    }
                })

        appDatabase.notificationDAO().readAll
                .observe(context as MainActivity, Observer<List<NotificationEntity>> {
                    notificationList ->
                    for (notification in notificationList as List<NotificationEntity>){
                        appDatabase.notificationDAO().deleteOne(notification)
                    }
                })

        appDatabase.relateDAO().readAll
                .observe(context as MainActivity, Observer<List<RelateEntity>> {
                    relateList ->
                    for (relate in relateList as List<RelateEntity>){
                        appDatabase.relateDAO().deleteOne(relate)
                    }
                })

        appDatabase.userDAO().readAll
                .observe(context as MainActivity, Observer<List<UserEntity>> {
                    userList ->
                    for (user in userList as List<UserEntity>){
                        appDatabase.userDAO().deleteOne(user)
                    }
                })

    }

    private fun saveUserToDB(userList: String?) {
        val jsonObj = JSONArray(userList)
        for (i in 0..(jsonObj.length() - 1)) {
            val item = jsonObj.getJSONObject(i)
            var user = UserEntity()
            user.id = item.optString("Id").toInt()
            user.alias = item.optString("alias")
            user.password = item.optString("password")

            try{
                appDatabase.userDAO().createOne(user)

            }catch(e: Exception){
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

            try{
                appDatabase.relateDAO().createOne(relate)

            }catch(e: Exception){
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

            try{
                appDatabase.notificationDAO().createOne(notif)

            }catch(e: Exception){
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

            try{
                appDatabase.commentDAO().createOne(comment)

            }catch(e: Exception){
            }
        }
    }

    private fun saveConfessionToDB(confessionList: String?) {
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

            try{
                appDatabase.confessionDAO().createOne(confession)

            }catch(e: Exception){
            }
        }
    }

}
