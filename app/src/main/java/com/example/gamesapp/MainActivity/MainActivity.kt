package com.example.gamesapp.MainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamesapp.Adapter.GameListAdapter
import com.example.gamesapp.DetailGamesActivity.DetailActivity
import com.example.gamesapp.DetailGamesActivity.DetailActivity.Companion.GAMES_ID
import com.example.gamesapp.FavoriteGamesActivity.FavoriteGamesActivity
import com.example.gamesapp.data.remote.ResultsItem
import com.example.gamesapp.databinding.ActivityMainBinding
import com.example.gamesapp.util.OnItemClickCallback
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel : MainViewModel
    private lateinit var gamesListAdapter : GameListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        showGamesLists()
        buttonAction()

    }

    private fun buttonAction(){
        binding.btnFavourite.setOnClickListener {
            val intent = Intent(this, FavoriteGamesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showGamesLists(){
        val rv = binding.rvGames
        mainViewModel.setGamesList().observe(this){ games ->
            gamesListAdapter = GameListAdapter(applicationContext, games)
            rv.layoutManager = LinearLayoutManager(applicationContext)
            rv.adapter = gamesListAdapter

            //click action
            gamesListAdapter.onItemClickCallback(object : OnItemClickCallback {
                override fun itemClicked(data: ResultsItem) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(GAMES_ID, data.id.toString())
                    startActivity(intent)
                }
            })

            //Search view action
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String?): Boolean {
                    if (binding.searchView.isEmpty()){
                        Toast.makeText(this@MainActivity, "Data Tidak Ada", Toast.LENGTH_SHORT).show()
                    }
                    return false
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    filterList(text, games)
                    return true
                }
            })
        }

        //observe loading state from viewModel
        mainViewModel.loading().observe(this){
            showLoading(it)
        }
    }

    private fun filterList(query : String?, list : List<ResultsItem>){
        var tempList = mutableListOf<ResultsItem>()
        if (query != null){
            for (i in 0..list.size - 1){
                if (list[i].name.toLowerCase(Locale.getDefault()).contains(query)){
                    tempList.add(list[i])
                }
            }
            if (tempList.isEmpty()){
                gamesListAdapter.setFilteredList(mutableListOf())
            }else{
                gamesListAdapter.setFilteredList(tempList)
            }
        }
    }

    private fun showLoading(isLoading : Boolean){
        if (isLoading){
            binding.pbMain.visibility = View.VISIBLE
        } else{
            binding.pbMain.visibility = View.GONE
        }
    }

}