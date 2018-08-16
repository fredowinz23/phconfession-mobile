package com.ph.confession.base.models

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * DB operations for ConfessionEntity
 */
@Dao
interface CommentDAO {

    @Insert
    fun createOne(commentEntity: CommentEntity)

    @Update
    fun updateOne(commentEntity: CommentEntity)

    @Delete
    fun deleteOne(commentEntity: CommentEntity)

    @get:Query("SELECT * FROM CommentEntity")
    val readAll: LiveData<List<CommentEntity>>

}