package com.muhdila.mygithubuser.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favourite(

    @PrimaryKey
    @ColumnInfo(name = "username")
    var username: String = "",

    @ColumnInfo(name = "homeUrl")
    var homeUrl: String? = null,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null
)