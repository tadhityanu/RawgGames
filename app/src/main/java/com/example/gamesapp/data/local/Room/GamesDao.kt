package com.example.gamesapp.data.local.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamesapp.data.local.GamesEntity

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGames(games : GamesEntity)

    @Query("SELECT count(*) FROM gamesDb WHERE gamesDb.id = :id")
    fun getGamesInDatabase(id : Int) : Int

    @Query("DELETE FROM gamesDb WHERE gamesDb.id = :id")
    fun removeFavoriteGames(id:Int) : Int

    @Query("SELECT * FROM gamesDb")
    fun getFavoriteGames() : List<GamesEntity>

}