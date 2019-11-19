package com.sogeti.inno.cherry.activities.ui.game


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.Game
import kotlinx.android.synthetic.main.layout_game.view.*
//  import com.bumptech.glide.Glide

class GameListAdapter (val games : List<Game>) : androidx.recyclerview.widget.RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_game, parent, false)
        )
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]

        holder.view.game_name.text = game.name
        holder.view.game_description.text = game.description
        holder.view.game_category.text = game.category

        /**      Glide.with(holder.view.context)
        .load(game.image)
        .into(holder.view.game_image)
         **/
    }

    class GameViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)


}



