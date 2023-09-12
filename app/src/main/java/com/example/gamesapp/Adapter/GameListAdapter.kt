package com.example.gamesapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamesapp.util.OnItemClickCallback
import com.example.gamesapp.data.remote.ResultsItem
import com.example.gamesapp.databinding.ItemGamesBinding

class GameListAdapter(private val context : Context, var gameList : List<ResultsItem>) : RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

    private lateinit var clickAction : OnItemClickCallback

    fun onItemClickCallback(itemClickCallback: OnItemClickCallback){
        this.clickAction = itemClickCallback
    }

    fun setFilteredList(list : MutableList<ResultsItem>){
        this.gameList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemGamesBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data : ResultsItem){
            val dataGenres = data.genres
            var listOfGenres = mutableListOf<String>()
            binding.apply {
                Glide.with(context)
                    .load(data.backgroundImage)
                    .into(imgGames)
                txtName.text = data.name
                txtRelease.text = "Release Date : ${data.released}"
                for (i in 0..dataGenres.size - 1){
                    listOfGenres.add(dataGenres[i].name)
                }
                val gamesGenre = listOfGenres.joinToString()
                txtGenre.text = "Genres : $gamesGenre"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =ItemGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gameList[position])
        holder.itemView.setOnClickListener{
            clickAction.itemClicked(gameList[holder.position])
        }
    }

}