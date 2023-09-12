package com.example.gamesapp.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailGamesResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("released")
    val released: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("background_image")
    val background_image: String,

    @field:SerializedName("developers")
    val developers: List<DevelopersItem>,

    @field:SerializedName("genres")
    val genres: List<GenresItem>,

    ) : Parcelable
