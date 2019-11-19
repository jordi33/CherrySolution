package com.sogeti.inno.cherry.activities.ui.moves

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.log4k.Log4k
import com.log4k.d
import com.log4k.i
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.ChoregraphyMove
import com.sogeti.inno.cherry.activities.ui.choregraphy.ChoregraphyMovesAdapter
import com.sogeti.inno.cherry.comm.*
import com.sogeti.inno.cherry.core.ControlServer
import com.sogeti.inno.cherry.utils.EmotionsEnum
import com.sogeti.inno.cherry.utils.IdleHandler
import com.sogeti.inno.cherry.utils.ToolbarHandler
import kotlinx.android.synthetic.main.layout_moves.*


class MovesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_moves)
        ToolbarHandler.initToolbar(this)

        // Since the layout of this app is pretty similar to the choregraphy one, we will use basically the same classes
        val movesList = getMoves()


        val adapter = MovesAdapter(this, movesList)
        moves_gv.adapter = adapter

        ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Bienvenue sur le jeu de mouvements. Tu peux cliquer sur les boutons pour que je le fasse")))

    }

    private fun getMoves(): ArrayList<ChoregraphyMove> {
        val movesList = ArrayList<ChoregraphyMove>()
        movesList.add(ChoregraphyMove("Dab", R.drawable.dab, "dab"))
        movesList.add(ChoregraphyMove("Shaking", R.drawable.shaking, "shaking"))
        movesList.add(ChoregraphyMove("Twist", R.drawable.twist, "twist"))
        movesList.add(ChoregraphyMove("Applaudissement", R.drawable.button_6, "clap_left_right"))
        return movesList
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        IdleHandler.resetCount()
    }
}




