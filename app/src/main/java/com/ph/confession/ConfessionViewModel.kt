package com.ph.confession

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.ph.confession.models.RoomDB


class ConfessionViewModel (application: Application) : AndroidViewModel(application) {

    private val appDatabase: RoomDB = RoomDB.getDatabase(this.getApplication())
    /** Get all activities from database */
    val confessionList = appDatabase.confessionDAO().readAll
}
