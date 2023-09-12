package com.example.gamesapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamesapp.util.OnItemClickCallback
import com.example.gamesapp.data.local.GamesEntity
import com.example.gamesapp.databinding.ItemGamesBinding
import com.example.gamesapp.util.OnFavoriteGamesClickCallback
import okhttp3.internal.notify

class FavoriteGamesAdapter(private val context : Context, var favGames : ArrayList<GamesEntity>) : RecyclerView.Adapter<FavoriteGamesAdapter.ViewHolder>() {

    private lateinit var clickAction : OnFavoriteGamesClickCallback

    fun onItemClickCallback(itemClickCallback: OnFavoriteGamesClickCallback){
        this.clickAction = itemClickCallback
    }

    fun setFavGamesData(list : List<GamesEntity>){
        favGames.clear()
        favGames.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding : ItemGamesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(dataFavGames : GamesEntity){
            binding.apply {
                txtName.text = dataFavGames.name
                txtRelease.text = dataFavGames.releaseDate
                txtGenre.text = dataFavGames.genres
                Glide.with(context)
                    .load(dataFavGames.image)
                    .into(imgGames)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favGames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favGames[position])
        holder.itemView.setOnClickListener{
            clickAction.itemFavClicked(favGames[holder.position])
        }
    }

}