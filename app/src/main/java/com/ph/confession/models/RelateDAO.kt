package com.ph.confession.models

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * DB operations for ConfessionEntity
 */
@Dao
interface RelateDAO {

    @Insert
    fun createOne(relateEntity: RelateEntity)

    @Update
    fun updateOne(relateEntity: RelateEntity)

    @Delete
    fun deleteOne(relateEntity: RelateEntity)

    @get:Query("SELECT * FROM RelateEntity")
    val readAll: LiveData<List<RelateEntity>>

}