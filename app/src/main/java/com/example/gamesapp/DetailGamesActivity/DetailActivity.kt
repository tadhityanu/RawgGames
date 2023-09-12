package com.example.gamesapp.DetailGamesActivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.gamesapp.data.local.Room.GamesDatabase
import com.example.gamesapp.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel : DetailViewModel
    val gamesDb by lazy { GamesDatabase.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)


        viewModelConfig()
        buttonAction()

    }

    @SuppressLint("SetTextI18n")
    private fun viewModelConfig(){
        val id = intent.getStringExtra(GAMES_ID)
        var genresList = mutableListOf<String>()
        var tempDeveloperList = mutableListOf<String>()
        detailViewModel.getGamesData(id.toString()).observe(this){ games ->
            val genres = games.genres
            val dev = games.developers
            for (genreIndex in 0..genres.size - 1){
                genresList.add(genres[genreIndex].name)
            }
            for (devIndex in 0..dev.size - 1){
                tempDeveloperList.add(dev[devIndex].name)
            }

            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(games.background_image)
                    .into(imgGames)
                txtName.text = games.name
                txtRelease.text = "Release Date : ${games.released}"
                txtGenre.text = "Genres : ${genresList.joinToString()}"
                txtDeveloper.text = "Developers : ${tempDeveloperList.joinToString()}"
                txtDescription.text = games.description
            }

            addToFavoriteAction(
                games.id,
                games.name,
                games.background_image,
                games.released,
                genresList.joinToString(),
                tempDeveloperList.joinToString(),
                games.description
            )
        }

        detailViewModel.loadingState().observe(this){
            showLoading(it)
        }

        detailViewModel.setItemAlpha().observe(this){
            itemAlpha(it)
        }

    }

    private fun buttonAction(){
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun addToFavoriteAction(
        id : Int,
        name : String,
        image : String,
        release : String,
        genres : String,
        developer : String,
        description : String
    ){
        var checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val favoriteGames = detailViewModel.getGamesById(id)
            withContext(Dispatchers.Main){
                if (favoriteGames != null){
                    if (favoriteGames > 0){
                        binding.btnFavourite.isChecked = true
                        checked = true
                    } else{
                        binding.btnFavourite.isChecked = false
                        checked = false
                    }
                }
            }
        }

        binding.btnFavourite.setOnClickListener{
            checked = !checked
            if (checked){
                detailViewModel.saveGameData(id, name, image, release, genres, developer, description)
            } else{
                detailViewModel.removeFavGames(id)
            }
            binding.btnFavourite.isChecked = checked
        }
    }

    private fun itemAlpha(alpha : Int){
        binding.desc.alpha = alpha.toFloat()
        binding.btnFavourite.alpha = alpha.toFloat()
    }

    private fun showLoading(loading : Boolean){
        if (loading){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        const val GAMES_ID = "games_id"
    }
}