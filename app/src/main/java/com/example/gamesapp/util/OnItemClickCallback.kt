package com.example.gamesapp.util

import com.example.gamesapp.data.local.GamesEntity
import com.example.gamesapp.data.remote.ResultsItem

interface OnItemClickCallback {
    fun itemClicked (data : ResultsItem)
}