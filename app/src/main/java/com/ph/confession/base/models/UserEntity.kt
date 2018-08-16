package com.ph.confession.base.models


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity
class UserEntity {

    /** Primary key (UUID) */
    @PrimaryKey(autoGenerate = true)
    var id: Int?  = null

    var alias: String? = null
    var password: String? = null

}









