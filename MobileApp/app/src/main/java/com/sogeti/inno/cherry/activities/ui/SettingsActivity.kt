package com.sogeti.inno.cherry.activities.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.appcompat.app.AppCompatActivity
import com.sogeti.inno.cherry.R
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragManager = this.supportFragmentManager
        fragManager.beginTransaction()
            .add(android.R.id.content, SettingsFragment())
            .commit()
    }

    class SettingsFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

        companion object { // This allows us to make these properties final and static accross the class
            const val SERVER_IP_KEY = "ip_address_preference"
            const val PORT_KEY = "port_preference"
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preference, rootKey)

            val sh: SharedPreferences = preferenceManager.sharedPreferences
            val serverIpPref: Preference = findPreference(SERVER_IP_KEY)
            serverIpPref.summary = sh.getString(SERVER_IP_KEY, "")
            val portPref: Preference = findPreference(PORT_KEY)
            portPref.summary = sh.getString(PORT_KEY, "")
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            if (key.equals(SERVER_IP_KEY)) {
                val pref: Preference = findPreference(key)
                val newValue = sharedPreferences?.getString(key, "")
                pref.summary = newValue
                if (newValue != null)
//                    ApiService.instance.server_address = newValue
                    true
            }
            else if (key.equals(PORT_KEY)) {
                val pref: Preference = findPreference(key)
                val newValue = sharedPreferences?.getString(key, "")
                pref.summary = newValue
                if (newValue != null)
//                    ApiService.instance.server_port = newValue.toInt()
                    true
            }
        }

        override fun onResume() {
            val sh: SharedPreferences = preferenceManager.sharedPreferences
            super.onResume()
            sh.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            val sh: SharedPreferences = preferenceManager.sharedPreferences
            super.onPause()
            sh.unregisterOnSharedPreferenceChangeListener(this)
        }
    }
}