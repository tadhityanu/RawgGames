package com.example.gamesapp.FavoriteGamesActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamesapp.Adapter.FavoriteGamesAdapter
import com.example.gamesapp.DetailGamesActivity.DetailActivity
import com.example.gamesapp.R
import com.example.gamesapp.data.local.GamesEntity
import com.example.gamesapp.databinding.ActivityFavoriteGamesBinding
import com.example.gamesapp.util.OnFavoriteGamesClickCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteGamesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteGamesBinding
    private lateinit var favGamesAdapter : FavoriteGamesAdapter
    private lateinit var favGamesViewModel: FavoriteGamesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonAction()
        favGamesViewModel = ViewModelProvider(this).get(FavoriteGamesViewModel::class.java)
        setRecyclerView()
        itemClickedAction()

    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val listFavGames = favGamesViewModel.getAllFavoriteGames()
            withContext(Dispatchers.Main){
                favGamesAdapter.setFavGamesData(listFavGames!!)
            }
        }
    }

    private fun setRecyclerView(){

        favGamesAdapter = FavoriteGamesAdapter(applicationContext, arrayListOf())
        binding.apply {
            rvFavoriteGames.layoutManager = LinearLayoutManager(applicationContext)
            rvFavoriteGames.adapter = favGamesAdapter
        }
    }

    private fun itemClickedAction(){
        favGamesAdapter.onItemClickCallback(object : OnFavoriteGamesClickCallback {
            override fun itemFavClicked(data: GamesEntity) {
                val intent = Intent(this@FavoriteGamesActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.GAMES_ID, data.id.toString())
                startActivity(intent)
            }
        })
    }

    private fun buttonAction(){
        binding.btnBack.setOnClickListener { finish() }
    }

}