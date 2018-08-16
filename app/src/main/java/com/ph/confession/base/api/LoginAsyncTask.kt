package com.ph.confession.base.api

//import android.app.ProgressDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.widget.Toast
import com.ph.confession.activities.LoginActivity
import com.ph.confession.activities.MainActivity
import com.ph.confession.base.Api
import com.ph.confession.viewmodels.UserViewModel
import org.json.JSONObject
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class LoginAsyncTask(private val loginActivity: LoginActivity) : AsyncTask<String, String, String>() {

//    private val dialog: ProgressDialog? = ProgressDialog(loginActivity)

    private val syncHttpHandler: SyncHttpHandler = SyncHttpHandler(loginActivity, Api().login)

    private val preferences: SharedPreferences = loginActivity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    private val viewModel = ViewModelProviders.of(loginActivity).get(UserViewModel::class.java)

    val loggedInUser = preferences.getString("alias", null)

    override fun onPreExecute() {
        // Before doInBackground
//        dialog!!.setMessage("Logging in..")
//        dialog.show()
    }

    override fun doInBackground(vararg params: String?): String {
        var urlParameters: String ?= null
        try {
            //create the parameters and pass on as a parameter
            urlParameters = "alias=" + URLEncoder.encode(params[0], "UTF-8") +
                    "&password=" + URLEncoder.encode(params[1], "UTF-8")
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
                val user = jsonResult.optString("user")
                saveInitialDataToDB(user)

//                    val viewModel = ViewModelProviders.of(loginActivity).get(SyncViewModel::class.java)
//                    // Pass context to viewModels
//                    viewModel.context = loginActivity
//                    // Call get records from web
//                    viewModel.getAll()
            }
            else
            {
                Toast.makeText(loginActivity, "Username and password not matched!", Toast.LENGTH_SHORT).show()
            }


        }catch(e: Exception){
            // This happens if error occurs
            Toast.makeText(loginActivity, "Something wrong with the connection!", Toast.LENGTH_SHORT).show()
        }

        // Dismiss progress dialog
//        if (dialog!!.isShowing) {
//            dialog.dismiss()
//        }

//        Log.e(TAG, "Result: " + result)
    }

    /**
     * Save Profile information to the database and initial value for lastSyncData
     *
     */
    private fun saveInitialDataToDB(user: String) {
        // Save Data for userEntity
        // Save Data to the database
        val userObj = JSONObject(user)

        val alias = userObj.optString("alias")

        viewModel.saveUserFromWebToAndroid(userObj)

        val usernamePreferences = preferences.edit()
        usernamePreferences.putString("alias", alias)
        usernamePreferences.apply()

        val intent = Intent(loginActivity, MainActivity::class.java)
        loginActivity.startActivity(intent)

    }

}