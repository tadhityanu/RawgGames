package com.example.gamesapp.MainActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamesapp.Api.ApiConfig
import com.example.gamesapp.data.remote.GamesResponse
import com.example.gamesapp.data.remote.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val getGamesList = MutableLiveData<List<ResultsItem>>()
    private val isLoading = MutableLiveData<Boolean>()
    private val alphaSet = MutableLiveData<Int>()

    fun setGamesList():LiveData<List<ResultsItem>>{
        val client = ApiConfig.getApiService().getGamesList(API_KEY, 1)
        isLoading.value = true
        client.enqueue(object : Callback<GamesResponse> {
            override fun onResponse(call: Call<GamesResponse>, response: Response<GamesResponse>) {
                if (response.isSuccessful){
                    isLoading.value = false
                    getGamesList.value = response.body()?.results
                } else{
                    isLoading.value = false
                    Log.d(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GamesResponse>, t: Throwable) {
                isLoading.value = false
                Log.d(TAG, "onResponse: ${t.message}")
            }
        })
        return getGamesList
    }

    fun loading() : LiveData<Boolean>{
        return isLoading
    }

    fun setItemAlpha() : LiveData<Int>{
        return alphaSet
    }

    companion object{
        const val TAG = "MainActivity"
        const val API_KEY = "5e44f8bfa75b4a40b3f2ef0a58987329"
    }

}