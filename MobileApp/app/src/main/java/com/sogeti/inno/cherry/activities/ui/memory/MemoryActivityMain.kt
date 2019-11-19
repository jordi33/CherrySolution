package com.sogeti.inno.cherry.activities.ui.memory

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.utils.ToolbarHandler
import kotlin.collections.ArrayList
import com.sogeti.inno.cherry.activities.MainActivity
import com.sogeti.inno.cherry.comm.CommandBuilder
import com.sogeti.inno.cherry.comm.MoveCommand
import com.sogeti.inno.cherry.comm.SpeakCommand
import com.sogeti.inno.cherry.core.ControlServer
import com.sogeti.inno.cherry.utils.IdleHandler

class MemoryActivityMain : AppCompatActivity() {
    var adapter: MemoryAdapter? = null
    var itemsList = ArrayList<CardItem>()
    var boardDim: Int = 3
    private val gameHelper: GameHelper = GameHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_main)
        ToolbarHandler.initToolbar(this)

        val memoryGridView = findViewById<GridView>(R.id.memory_gv)
        adapter = MemoryAdapter(this, itemsList)
        memoryGridView.adapter = adapter
        changeBoardSize(4)
        //initSizeButtons()

        ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Bienvenue sur le jeu de cases mémoires. Appuis sur les images pour trouver les paires !")))
    }
/*
    private fun initSizeButtons() {
        val firstButton = findViewById<Button>(R.id.memory_game_first_size_btn)
        val secondButton = findViewById<Button>(R.id.memory_game_second_size_btn)
        val thirdButton = findViewById<Button>(R.id.memory_game_third_size_btn)

        firstButton.setOnClickListener {
            changeBoardSize(3)
        }
        secondButton.setOnClickListener {
            changeBoardSize(4)
        }
        thirdButton.setOnClickListener {
            changeBoardSize(5)
        }
    } */

    private fun changeBoardSize(size: Int) {
        boardDim = size
        val gameGrid = findViewById<GridView>(R.id.memory_gv)
        gameGrid.numColumns = size
        itemsList = ArrayList<CardItem>()
        when(size) {
            3 -> {
                itemsList.add(CardItem( R.drawable.button_1))
                itemsList.add(CardItem( R.drawable.button_2))
                itemsList.add(CardItem( R.drawable.button_3))
                itemsList.add(CardItem( R.drawable.button_4))
                itemsList.add(CardItem( R.drawable.button_5))
                itemsList.add(CardItem( R.drawable.button_6))
                gameGrid.layoutParams.width = 3*250
            }
            4 -> {
                itemsList.add(CardItem( R.drawable.pokeball))
                itemsList.add(CardItem( R.drawable.psyduck))
                itemsList.add(CardItem( R.drawable.charmander))
                itemsList.add(CardItem( R.drawable.pikachu))
                itemsList.add(CardItem( R.drawable.button_5))
                itemsList.add(CardItem( R.drawable.squirtle))
                itemsList.add(CardItem( R.drawable.eevee))
                itemsList.add(CardItem( R.drawable.bullbasaur))
                gameGrid.layoutParams.width = 4*450
            }
            5 -> {
                itemsList.add(CardItem( R.drawable.button_1))
                itemsList.add(CardItem( R.drawable.button_2))
                itemsList.add(CardItem( R.drawable.button_3))
                itemsList.add(CardItem( R.drawable.button_4))
                itemsList.add(CardItem( R.drawable.button_5))
                itemsList.add(CardItem( R.drawable.button_6))
                itemsList.add(CardItem( R.drawable.button_7))
                itemsList.add(CardItem( R.drawable.button_8))
                itemsList.add(CardItem( R.drawable.button_9))
                itemsList.add(CardItem( R.drawable.button_10))
                gameGrid.layoutParams.width = 5*250
            }
        }
        val copiedArray = itemsList.map { it.copy() }
        itemsList.addAll(copiedArray)
        itemsList.shuffle()
        adapter!!.changeGameBoard(itemsList)
    }

    // setup time counter from 0s
    fun updateCounterText(value: String) {
        //val counterTextView = findViewById<TextView>(R.id.counterTextView)
        //counterTextView.text = value
    }

    fun updateTrialsCounter() {
        gameHelper.raiseAttemptCounter()
    }

    fun showFinishDialog() {
        gameHelper.isFinished = true

        Thread.sleep(1750)
        //val message = "Tu as gagné après ${gameHelper.attemptCounter} essais et en ${gameHelper.secondsPassed} secondes"
        val message = "Youhou, tu as gagné ! tu es vraiment un chef !"
        ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("$message")))
        ControlServer.sendCommand(CommandBuilder.toJson((MoveCommand("twist"))))
        val finalDialog = AlertDialog.Builder(this)
            .setTitle("Terminé")
            .setMessage(message)
            .setPositiveButton("Nouvelle partie") { _, _ -> recreate() }
//            .setNegativeButton("Quitter") { _, _ -> System.exit(0) }
            .setNegativeButton("Quitter") { _, _ ->
                startActivity(Intent(this, MainActivity::class.java))
            }
            .create()
        finalDialog.show()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        IdleHandler.resetCount()
    }
}

