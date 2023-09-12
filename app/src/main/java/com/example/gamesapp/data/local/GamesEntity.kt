package com.example.gamesapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gamesDb")
data class GamesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Int,

    @ColumnInfo(name = "name")
    var name : String,

    @ColumnInfo(name = "image")
    var image : String,

    @ColumnInfo(name = "releaseDate")
    var releaseDate : String,

    @ColumnInfo(name = "genres")
    var genres : String,

    @ColumnInfo(name = "developers")
    var developers : String,

    @ColumnInfo(name = "description")
    var description : String,

    @ColumnInfo(name = "isBookmarked")
    var isBookmarked : Boolean = false,
)