package com.sogeti.inno.cherry.activities.ui.wordImages

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.MainActivity
import com.sogeti.inno.cherry.comm.CommandBuilder
import com.sogeti.inno.cherry.comm.SpeakCommand
import com.sogeti.inno.cherry.core.ControlServer
import com.sogeti.inno.cherry.utils.ToolbarHandler

class WordImagesActivityMain : AppCompatActivity() {

    private lateinit var chosenArea : ChosenArea
    private lateinit var availableArea : AvailableArea
    private lateinit var winScreen : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_four_images_one_word)
        ToolbarHandler.initToolbar(this)
        winScreen = findViewById(R.id.winScreen)
        Level.loadAll(this)
        setOnNextLevelClick()
        hideWinMenu()
        loadCurrentLevel()

        ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Ce jeu consiste à trouver le mot associé aux quatres images composé des lettres affichées. Il suffit de cliquer sur une lettre pour l'ajouter au mot final, tu sauras quand tu auras la bonne réponse!")))

    }

    private fun setOnNextLevelClick() {
        val button = findViewById<Button>(R.id.nextLevel)
        button.setOnClickListener {
            hideWinMenu()
            nextLevel()
        }
    }

    private fun nextLevel(){
        if( Level.hasNext()) {
            Level.next()
            loadCurrentLevel()
        } else {
            goToMainMenu()
        }
    }

    private fun goToMainMenu(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun getImageFromName(name: String): Drawable {
        return getDrawable(resources.getIdentifier(name, "drawable", packageName))!!
    }

    fun choseLetter(lb : LetterButton){
        if( !chosenArea.isFull()){
            availableArea.removeLetter(lb)
            chosenArea.addLetter(lb)
            if( checkWin()){
                showWinMenu()
            }
        }
    }

    fun unChoseLetter(lb : LetterButton){
        chosenArea.removeLetter(lb)
        availableArea.addLetter(lb)
    }

    fun hideWinMenu(){
        winScreen.visibility = LinearLayout.GONE
    }

    fun showWinMenu(){
        winScreen.visibility = LinearLayout.VISIBLE
    }

    fun loadCurrentLevel(){
        chosenArea =  ChosenArea(this)
        availableArea = AvailableArea(this)
        findViewById<ImageView>(R.id.content).setImageDrawable(Level.current.image)
    }

    fun checkWin()  = Level.current.check(chosenArea.getChosenWord())

}
