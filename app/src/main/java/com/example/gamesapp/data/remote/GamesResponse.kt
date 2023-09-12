package com.example.gamesapp.data.remote

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GamesResponse(

	@field:SerializedName("next")
	val next: String,

	@field:SerializedName("previous")
	val previous: String,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("results")
	val results: List<ResultsItem>
) : Parcelable

@Parcelize
data class ResultsItem(

    @field:SerializedName("background_image")
	val backgroundImage: String,

    @field:SerializedName("genres")
	val genres: List<GenresItem>,

    @field:SerializedName("name")
	val name: String,

    @field:SerializedName("id")
	val id: Int,

    @field:SerializedName("slug")
	val slug: String,

    @field:SerializedName("released")
	val released: String,

    @field:SerializedName("developers")
	val developers: List<DevelopersItem>
) : Parcelable

@Parcelize
data class GenresItem(

	@field:SerializedName("games_count")
	val gamesCount: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("image_background")
	val imageBackground: String,

	@field:SerializedName("slug")
	val slug: String
) : Parcelable

@Parcelize
data class DevelopersItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("slug")
	val slug: String,

	@field:SerializedName("games_count")
	val games_count: Int,

	@field:SerializedName("image_background")
	val image_background: String,
) : Parcelable
