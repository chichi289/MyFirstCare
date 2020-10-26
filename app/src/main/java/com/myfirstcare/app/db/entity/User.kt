package com.myfirstcare.app.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User{
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0

    @ColumnInfo(name = "email")
    lateinit var email: String

    @ColumnInfo(name = "password")
    lateinit var password: String
}