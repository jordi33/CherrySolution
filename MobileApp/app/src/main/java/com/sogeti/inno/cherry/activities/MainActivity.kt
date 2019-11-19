package com.sogeti.inno.cherry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.log4k.Level
import com.log4k.Log4k
import com.log4k.android.AndroidAppender
import com.log4k.android.BuildConfig
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.comm.*
import com.sogeti.inno.cherry.core.ControlServer
import com.log4k.d
import com.sogeti.inno.cherry.utils.*
import es.dmoral.toasty.Toasty


class MainActivity : Commandable, AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridView = findViewById<GridView>(R.id.gridview)
        gridView?.adapter = HomeButtonsAdapter(this)

        this.initApp()
        ControlServer.sendCommand(CommandBuilder.toJson(ResetPoppy()))

    }

    private fun initApp() {
        if (BuildConfig.DEBUG) {
            Log4k.add(Level.Verbose, ".*", AndroidAppender())
        }
        ToolbarHandler.initToolbar(this)
        Assets.setAssets(this.assets)
        ControlServer.start()
        ControlServer.subscribe(this)
        Toasty.Config.getInstance()
            .tintIcon(true) // optional (apply textColor also to the icon)
            .apply() // required
        ControlServer.sendCommand(CommandBuilder.toJson(GetJokes())) // Retrieve the Jokes on application Start
        ControlServer.sendCommand(CommandBuilder.toJson(GetCalculatorItems())) // Retrieve the CalculatorItems on application Start
        ControlServer.sendCommand(CommandBuilder.toJson(GetChoregraphies())) // Retrieve the Choregraphies
        ControlServer.sendCommand(CommandBuilder.toJson(GetTales())) // Retrieve the tales

        IdleHandler // initialize
    }

    override fun onReceiveCommand(command: Command) {
        if (command is RetrieveConnectedPoppiesResponse) {
            ConnectedPoppyHolder.instance.connectedPoppies = command.ConnectedPoppies
            if (command.ConnectedPoppies.size == 1){
                ControlServer.sendCommand(CommandBuilder.toJson(ConnectToPoppy(command.ConnectedPoppies[0])))
                Log.d("Linking with Poppy", command.ConnectedPoppies[0])
                ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Bienvenue sur mon application! <break time=\"200ms\"/> Tu peux sélectionner différents jeux pour qu'on s'amuse ensemble")))
            }
        }
        if (command is JokesResponse) {
            JokesHolder.instance.jokesList = command.Jokes
        }
        if (command is CalculatorItemsResponse) {
            CalculatorItemsHolder.instance.ItemsArray = command.CalculatorItems
        }
        if (command is GetChoregraphiesResponse) {
            ChoregraphiesHolder.instance.initChoregraphyMoves(command.Choregraphies)
        }
        if (command is GetTalesResponse) {
            TalesHolder.instance.talesList = command.Tales
        }

        else if (command is ConnectionToPoppyLost) {
            val runnable = Runnable {
                fun run() {
                    Toasty.error(this, "Connection au Robot perdue", Toast.LENGTH_LONG).show()
                }
            }
            runOnUiThread(runnable)
        }
        Log4k.d("Received command $command")
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        IdleHandler.resetCount()
    }
}
