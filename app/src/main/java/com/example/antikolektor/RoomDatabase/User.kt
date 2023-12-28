package com.example.antikolektor.RoomDatabase


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val id: Int,
    val type: String ,
    val phone: String,
    val subtype: String ,
    val comment: String
    )

@Entity(tableName = "base_phones")
data class Base(
    @PrimaryKey
    val id: Int ,
    val type: String ,
    val phone: String ,
    val subtype: String ,
    val comment: String
)