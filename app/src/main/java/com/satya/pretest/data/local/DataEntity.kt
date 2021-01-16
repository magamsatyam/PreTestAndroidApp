package com.example.satya.data.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class DataEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

//    @ColumnInfo(name ="airing")
//    val airing: Boolean,
//
//    @NonNull
//    @ColumnInfo(name ="end_date")
//    val end_date: String,
//
//    @ColumnInfo(name ="episodes")
//    val episodes: Int,

    @ColumnInfo(name ="image_url")
    val image_url: String,

//    @ColumnInfo(name ="mal_id")
//    val mal_id: Int,

//    @ColumnInfo(name ="members")
//    val members: Int,

//    @ColumnInfo(name ="rated")
//    val rated: String,

//    @ColumnInfo(name ="score")
//    val score: Double,

//    @ColumnInfo(name ="start_date")
//    val start_date: String,

    @ColumnInfo(name ="synopsis")
    val synopsis: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "type")
    val type: String,

//    @ColumnInfo(name = "url")
//    val url: String

)

