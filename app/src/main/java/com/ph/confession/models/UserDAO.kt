package com.ph.confession.models

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * DB operations for ConfessionEntity
 */
@Dao
interface UserDAO {

    @Insert
    fun createOne(userEntity: UserEntity)

    @Update
    fun updateOne(userEntity: UserEntity)

    @Delete
    fun deleteOne(userEntity: UserEntity)

    @get:Query("SELECT * FROM UserEntity")
    val readAll: LiveData<List<UserEntity>>

}