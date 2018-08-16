package com.ph.confession.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.ph.confession.base.models.RoomDB
import com.ph.confession.base.models.UserEntity
import org.json.JSONObject

class UserViewModel (application: Application) : AndroidViewModel(application) {

    /** Instantiate Database */
    private val appDatabase: RoomDB = RoomDB.getDatabase(this.getApplication())
    /** Get all activities from database */
    val userList = appDatabase.userDAO().readAll

    fun saveUserFromWebToAndroid(userObj: JSONObject) {

        val user = UserEntity()
        user.alias = userObj.optString("alias")
        user.password = userObj.optString("password")

        appDatabase.userDAO().createOne(user)
    }

 }
