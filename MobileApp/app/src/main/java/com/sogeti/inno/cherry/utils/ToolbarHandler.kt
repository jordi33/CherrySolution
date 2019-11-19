package com.sogeti.inno.cherry.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.MainActivity
import com.sogeti.inno.cherry.activities.PoppySelectionActivity

object ToolbarHandler {
    fun initToolbar(context: AppCompatActivity) {
        context.setSupportActionBar(context.findViewById(R.id.toolbar_cherry))
        val homeBtn = context.findViewById<ImageButton>(R.id.home_btn)
        homeBtn.setOnClickListener{
            val intent = Intent(context as Context, MainActivity::class.java)
            context.startActivity(intent)
        }
        val settingsBtn = context.findViewById<ImageButton>(R.id.settings_btn)
        settingsBtn.setOnClickListener{
            val intent = Intent(context as Context, PoppySelectionActivity::class.java)
            context.startActivity(intent)
        }
    }
}