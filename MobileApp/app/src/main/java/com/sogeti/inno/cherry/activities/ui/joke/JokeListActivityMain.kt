package com.sogeti.inno.cherry.activities.ui.joke

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.comm.CommandBuilder
import com.sogeti.inno.cherry.comm.Joke
import com.sogeti.inno.cherry.comm.SpeakCommand
import com.sogeti.inno.cherry.core.ControlServer
import com.sogeti.inno.cherry.utils.IdleHandler
import com.sogeti.inno.cherry.utils.JokesHolder
import com.sogeti.inno.cherry.utils.ToolbarHandler
import es.dmoral.toasty.Toasty
import java.util.*

class JokeListActivityMain : AppCompatActivity() {

    private var jokeList: ArrayList<Joke> = ArrayList()
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joke_layout)
        ToolbarHandler.initToolbar(this)

        val contentList = JokesHolder.instance.jokesList
        if (contentList == null) {
            Toasty.error(this, "Aucune blague récupérée", Toast.LENGTH_LONG).show()
        }
        else {
            val contentIterator = contentList.iterator() // Check for null
            for (content in contentIterator) {
                jokeList.add(Joke(content))
            }
            initButtons()
            ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Ici, tu peux naviguer entre les blagues avec les flèches")))
        }
    }

    private fun initButtons() {
        val leftButton = findViewById<ImageButton>(R.id.left_joke_button)
        val rightButton = findViewById<ImageButton>(R.id.right_joke_button)

        leftButton.setOnClickListener {
            if (currentIndex > 0)
                currentIndex--
            else
                currentIndex = jokeList.size - 1
            updateJoke()
        }

        rightButton.setOnClickListener {
            if (currentIndex < (jokeList.size - 1))
                currentIndex++
            else
                currentIndex = 0
            updateJoke()
        }
        updateJoke()
    }

    private fun updateJoke() {
        val jokeTextView = findViewById<TextView>(R.id.joke_content)
        jokeTextView.text = jokeList[currentIndex].Content
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        IdleHandler.resetCount()
    }
}
