package com.ph.confession.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.ph.confession.SelectCategoryActivity
import com.ph.confession.base.api.ComposeAsyncTask
import com.ph.confession.base.models.ConfessionEntity
import com.ph.confession.base.models.RoomDB

/**
 * ViewModel of actvpack
 *
 */
class ConfessionViewModel (application: Application) : AndroidViewModel(application) {

    /** Instantiate Database */
    private val appDatabase: RoomDB = RoomDB.getDatabase(this.getApplication())
    /** Get all activities from database */
    val confessionList = appDatabase.confessionDAO().readAll

    fun saveToDB(context: SelectCategoryActivity, conf: ConfessionEntity) {

        //Create in web
        ComposeAsyncTask(context, conf).execute()
        // Create in local
//        appDatabase.confessionDAO().createOne(conf)

    }

//    fun updateDraftRecord(context: StudentDraftItemActivity, ticket: TicketEntity) {
//
//        // Update web
//        UpdateDraftTicketAsyncTask(context, ticket).execute()
//        // Update local
//        appDatabase.ticketDAO().updateOne(ticket)
//
//    }
//
//    fun updateStatusToReturn(context: MainActivity, ticket: TicketEntity) {
//
//        // Update in web
//        UpdateStatusToReturnAsyncTask(context, ticket).execute()
//        // Update local
//        appDatabase.ticketDAO().updateOne(ticket)
//
//    }

}
