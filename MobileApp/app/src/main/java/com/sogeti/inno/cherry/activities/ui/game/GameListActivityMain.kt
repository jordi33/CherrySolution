package com.sogeti.inno.cherry.activities.ui.game


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.Game
import kotlinx.android.synthetic.main.activity_game_list_main.*


class GameListActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list_main)


 /**
        // refresh data from API on
game_refresh_layout.setOnRefreshListener {
    fetchGame();
}
        // refresh data on application load
        fetchGame()

**/
////////////////////////////////////////////////////////////////////
        // Dummy data to delete when connected to API from here

        val games = listOf(

            Game(1, "game1", "image1", "description1", "catergory1"),
            Game(10, "game10", "image10", "description10", "catergory10"),
            Game(11, "game11", "image11", "description11", "catergory11"),
            Game(12, "game12", "image12", "description12", "catergory12"),
            Game(13, "game13", "image13", "description13", "catergory13"),
            Game(14, "game14", "image14", "description14", "catergory14"),
            Game(15, "game15", "image15", "description15", "catergory15"),
            Game(16, "game16", "image16", "description16", "catergory16"),
            Game(17, "game17", "image17", "description17", "catergory17"),
            Game(18, "game18", "image18", "description18", "catergory18"),
            Game(19, "game19", "image19", "description19", "catergory19")
        )


//        recyclerViewGame.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
//        recyclerViewGame.adapter = GameListAdapter(games)

        // To here : End of code to delete when connected to API

    }


    /**
    // Comment to use dummy data from here

    private fun fetchGame() {


        GameApi().getGame().enqueue(object : Callback<List<Game>> {

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                game_refresh_layout.isRefreshing = false
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                game_refresh_layout.isRefreshing = false
                val game =  response.body()
                game?.let {
                    showGame(it)
                }
            }

        })

        // to here

    }


    //  Comment to use dummy data from here

    private fun showGame(game: List<Game>) {
    recyclerViewGame.layoutManager = LinearLayoutManager(this)
    recyclerViewGame.adapter = GameListAdapter(game)
    }

    // to here

 **/

}



