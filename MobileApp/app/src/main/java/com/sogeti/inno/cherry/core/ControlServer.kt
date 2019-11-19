package com.sogeti.inno.cherry.core

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.log4k.Log4k
import com.log4k.e
import com.sogeti.inno.cherry.activities.ui.tales.TalesActivity
import com.sogeti.inno.cherry.comm.*

object ControlServer : IControlServer, IConnectionListener {
    private val server = ServerConnector(this)
    private var consumer: Commandable? = null

    fun subscribe(c : Commandable){
        this.consumer = c
    }

    override fun sendCommand(command: String?) {
        if (command != null) {
            server.send(command)
        }
    }

    override fun onOpen() {

    }

    override fun onClose() {
        Log4k.e("Connection closed")
    }

    override fun onMessage(text: String) {
        this.consumer!!.onReceiveCommand(CommandBuilder.build(text)!!)
    }

    fun start() {
        server.connect()
    }
}