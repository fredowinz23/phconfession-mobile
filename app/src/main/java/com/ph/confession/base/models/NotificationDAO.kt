package com.ph.confession.base.models


import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * DB operations for ConfessionEntity
 */
@Dao
interface NotificationDAO {

    @Insert
    fun createOne(notificationEntity: NotificationEntity)

    @Update
    fun updateOne(notificationEntity: NotificationEntity)

    @Delete
    fun deleteOne(notificationEntity: NotificationEntity)

    @get:Query("SELECT * FROM NotificationEntity")
    val readAll: LiveData<List<NotificationEntity>>

}