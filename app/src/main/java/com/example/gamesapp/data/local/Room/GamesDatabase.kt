package com.example.gamesapp.data.local.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gamesapp.data.local.GamesEntity

@Database(entities = [GamesEntity::class], version = 3, exportSchema = false)
abstract class GamesDatabase : RoomDatabase(){

    abstract fun gamesDao() : GamesDao

    companion object{
        @Volatile
        private var instance : GamesDatabase? = null
        fun getInstance(context: Context) : GamesDatabase =
            instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    GamesDatabase::class.java, "games.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }

}