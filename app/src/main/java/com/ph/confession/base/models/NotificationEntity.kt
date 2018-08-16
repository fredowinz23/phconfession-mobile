package com.ph.confession.base.models


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity
class NotificationEntity {

    /** Primary key (UUID) */
    @PrimaryKey(autoGenerate = true)
    var id: Int?  = null

    var recepient: String? = null
    var message: String? = null
    var confessionId: String? = null
    var commentId: String? = null
    var type: String? = null
    var status: String? = null
    var datetime: String? = null

}









