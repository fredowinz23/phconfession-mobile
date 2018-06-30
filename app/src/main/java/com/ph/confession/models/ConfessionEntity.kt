package com.ph.confession.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity
class ConfessionEntity {

    /** Primary key (UUID) */
    @PrimaryKey()
    var id: String = UUID.randomUUID().toString()

    var alias: String? = null
    var category: String? = null
    var title: String? = null
    var message: String? = null
    var location: String? = null
    var datetime: String? = null
    var view: Int? = null
    var lastChange: String? = null

}









