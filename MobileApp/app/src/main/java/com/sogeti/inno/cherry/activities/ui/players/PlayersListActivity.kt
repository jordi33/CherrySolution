package com.sogeti.inno.cherry.activities.ui.players

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sogeti.inno.cherry.R
import kotlinx.android.synthetic.main.activity_players_list.*

class PlayersListActivity : AppCompatActivity() {

 //   private var name: ArrayList<Child> = ArrayList()





    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players_list)
        linearLayoutManager = LinearLayoutManager(this)
        playersRecyclerView.layoutManager = linearLayoutManager

    }
}
