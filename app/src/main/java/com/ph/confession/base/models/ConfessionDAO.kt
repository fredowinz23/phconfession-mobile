package com.ph.confession.base.models

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * DB operations for ConfessionEntity
 */
@Dao
interface ConfessionDAO {

    @Insert
    fun createOne(confessionEntity: ConfessionEntity)

    @Update
    fun updateOne(confessionEntity: ConfessionEntity)

    @Delete
    fun deleteOne(confessionEntity: ConfessionEntity)

    @get:Query("SELECT * FROM ConfessionEntity")
    val readAll: LiveData<List<ConfessionEntity>>

}