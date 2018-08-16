package com.ph.confession.base.models


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity
class RelateEntity {

    /** Primary key (UUID) */
    @PrimaryKey(autoGenerate = true)
    var id: Int?  = null

    var cId: String? = null
    var alias: String? = null

}









