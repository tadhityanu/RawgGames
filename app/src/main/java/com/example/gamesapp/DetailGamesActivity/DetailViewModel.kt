package com.example.gamesapp.DetailGamesActivity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamesapp.Api.ApiConfig
import com.example.gamesapp.data.local.GamesEntity
import com.example.gamesapp.data.local.Room.GamesDao
import com.example.gamesapp.data.local.Room.GamesDatabase
import com.example.gamesapp.data.remote.DetailGamesResponse
import com.example.gamesapp.data.remote.DevelopersItem
import com.example.gamesapp.data.remote.GenresItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val gamesData = MutableLiveData<DetailGamesResponse>()
    private val genreData = MutableLiveData<List<GenresItem>>()
    private val developersData = MutableLiveData<List<DevelopersItem>>()
    private val isLoading = MutableLiveData<Boolean>()
    private val alphaSet = MutableLiveData<Int>()

    private var gamesDao : GamesDao?
    private var gamesDb : GamesDatabase?

    init {
        gamesDb = GamesDatabase.getInstance(application)
        gamesDao = gamesDb?.gamesDao()
    }

    fun getGamesData(id : String) : LiveData<DetailGamesResponse>{
        val client = ApiConfig.getApiService().getDetailGames(id, API_KEY)
        alphaSet.value = 0
        isLoading.value = true
        client.enqueue(object : Callback<DetailGamesResponse> {
            override fun onResponse(call: Call<DetailGamesResponse>, response: Response<DetailGamesResponse>) {
                if (response.isSuccessful){
                    alphaSet.value = 1
                    isLoading.value = false
                    gamesData.value = response.body()
                    genreData.value = response.body()?.genres
                    developersData.value = response.body()?.developers
                } else{
                    isLoading.value = false
                    alphaSet.value = 1
                    Log.d(TAG, "onResponse: ${response.message()} ")
                }
            }
            override fun onFailure(call: Call<DetailGamesResponse>, t: Throwable) {
                isLoading.value = false
                alphaSet.value = 0
                Log.d(TAG, "onResponse: ${t.message} ")
            }
        })

        return gamesData
    }

    fun saveGameData(
        id : Int,
        name : String,
        image : String,
        release : String,
        genres : String,
        developer : String,
        description : String
    ){
        CoroutineScope(Dispatchers.IO).launch {
            var games =GamesEntity(
                id,name,image,release,genres,developer,description, true
            )
            gamesDao?.insertGames(games)
        }
    }

    fun getGamesById(id:Int) = gamesDao?.getGamesInDatabase(id)

    fun removeFavGames(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            gamesDao?.removeFavoriteGames(id)
        }
    }

    fun loadingState():LiveData<Boolean>{
        return isLoading
    }

    fun setItemAlpha() : LiveData<Int>{
        return alphaSet
    }

    companion object{
        val TAG = "DetailActivity"
        const val API_KEY = "5e44f8bfa75b4a40b3f2ef0a58987329"
    }

}