package com.example.gamesapp.FavoriteGamesActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gamesapp.data.local.GamesEntity
import com.example.gamesapp.data.local.Room.GamesDao
import com.example.gamesapp.data.local.Room.GamesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteGamesViewModel(app:Application) : AndroidViewModel(app) {

    private var gamesDao : GamesDao?
    private var gamesDb : GamesDatabase?

    init {
        gamesDb = GamesDatabase.getInstance(app)
        gamesDao = gamesDb?.gamesDao()
    }

    fun getAllFavoriteGames() = gamesDao?.getFavoriteGames()


}