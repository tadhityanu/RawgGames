package com.example.gamesapp.Api

import com.example.gamesapp.data.remote.DetailGamesResponse
import com.example.gamesapp.data.remote.GamesResponse
import com.example.gamesapp.data.remote.ResultsItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    fun getGamesList(
        @Query("key") key : String,
        @Query("page") page : Int
    ) : Call<GamesResponse>

    @GET("games/{id}")
    fun getDetailGames(
        @Path("id") id:String,
        @Query("key") key : String
    ) : Call<DetailGamesResponse>

}