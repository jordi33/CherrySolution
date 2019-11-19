package com.sogeti.inno.cherry.activities.ui.tales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.Tale
import com.sogeti.inno.cherry.comm.CommandBuilder
import com.sogeti.inno.cherry.comm.SpeakCommand
import com.sogeti.inno.cherry.core.ControlServer
import com.sogeti.inno.cherry.utils.IdleHandler
import com.sogeti.inno.cherry.utils.TalesHolder
import com.sogeti.inno.cherry.utils.ToolbarHandler

class TalesActivity : AppCompatActivity() {
    lateinit var currentTale: Tale

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tales)
        ToolbarHandler.initToolbar(this)

        val currentIndex = intent.getIntExtra("tales_index", 0)
        currentTale = TalesHolder.instance.talesList[currentIndex]

        val textContentTextView = findViewById<TextView>(R.id.tale_content)
        textContentTextView.movementMethod = ScrollingMovementMethod()
        textContentTextView.text = Html.fromHtml(currentTale.Content, Html.FROM_HTML_MODE_COMPACT).toString()

        val titleTextView = findViewById<TextView>(R.id.tale_title)
        titleTextView.text = currentTale.Name

        val playTaleBtn = findViewById<Button>(R.id.play_tale_btn)
        playTaleBtn.setOnClickListener {
            ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand(currentTale.Content)))
        }

    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        IdleHandler.resetCount()
    }
}
