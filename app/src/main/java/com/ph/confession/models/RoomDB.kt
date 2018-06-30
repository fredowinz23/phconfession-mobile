package com.ph.confession.models

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(version = 1,
          entities = [ConfessionEntity::class])
abstract class RoomDB : RoomDatabase() {

    // DAO objects
    abstract fun confessionDAO(): ConfessionDAO

    // DB Instance
    companion object {

        private var INSTANCE: RoomDB? = null

        fun getDatabase(context: Context): RoomDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                                                RoomDB::class.java,
                                                "PHConfession.DB")
                                                .allowMainThreadQueries()
                                                .build()
            }
            return INSTANCE!!
        }
    }
}


