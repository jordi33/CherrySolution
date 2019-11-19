package com.sogeti.inno.cherry.activities

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.log4k.Log4k
import com.log4k.e
import com.log4k.i
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.comm.*
import com.sogeti.inno.cherry.core.ControlServer
import com.sogeti.inno.cherry.utils.ConnectedPoppyHolder
import com.sogeti.inno.cherry.utils.ToolbarHandler
import es.dmoral.toasty.Toasty
import java.lang.Exception

class PoppySelectionActivity :Commandable, AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poppy_selection)
        ToolbarHandler.initToolbar(this)
        var connectedPoppies: List<String> = listOf("")
        try {
            connectedPoppies = ConnectedPoppyHolder.instance.connectedPoppies as List<String>
        } catch (exception: Exception) {
            Log4k.e("Cast to string impossible", exception)
            Toasty.error(this, "Erreur de récupération des Poppy connectées", Toast.LENGTH_LONG).show()
            ControlServer.subscribe(this)
            ControlServer.sendCommand(CommandBuilder.toJson(RetrieveConnectedPoppies()))
        }
        if (connectedPoppies.isNotEmpty()) {
            Log4k.i("Connected Poppies: $connectedPoppies")
            val adapter = ArrayAdapter(this,
                R.layout.poppy_selection_item, connectedPoppies)
            val listView: ListView = findViewById(R.id.poppy_list_view)
            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, position, _ ->

                // value of item that is clicked
                val itemValue = listView.getItemAtPosition(position) as String
                ControlServer.sendCommand(CommandBuilder.toJson(ConnectToPoppy(itemValue)))
                // Toast the values
                Toasty.success(this, "Application liée avec la Poppy numéro $itemValue", Toast.LENGTH_LONG).show()
                ConnectedPoppyHolder.connected = true
            }
        }
        else {
            val titleView = findViewById<TextView>(R.id.connected_poppies_title)
            titleView.text = getString(R.string.no_connected_poppies_title_text)
        }
    }

    override fun onReceiveCommand(command: Command) {
        if (command is RetrieveConnectedPoppiesResponse) {
            ConnectedPoppyHolder.instance.connectedPoppies = command.ConnectedPoppies
            if (command.ConnectedPoppies.size == 1){
                ControlServer.sendCommand(CommandBuilder.toJson(ConnectToPoppy(command.ConnectedPoppies[0])))
                Log.d("Linking with Poppy", command.ConnectedPoppies[0])
            }
            recreate()
        }
    }
}