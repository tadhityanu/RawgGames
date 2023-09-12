package com.example.gamesapp.util

import com.example.gamesapp.data.local.GamesEntity
import com.example.gamesapp.data.remote.ResultsItem

interface OnFavoriteGamesClickCallback {
    fun itemFavClicked (data : GamesEntity)
}