package com.ph.confession.base.models


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity
class CommentEntity {

    /** Primary key (UUID) */
    @PrimaryKey(autoGenerate = true)
    var id: Int?  = null

    var cId: Int? = null
    var comment: String? = null
    var title: String? = null
    var alias: String? = null
    var datetime: String? = null

}









