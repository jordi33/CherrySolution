package com.sogeti.inno.cherry.activities.ui.calculator

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.utils.ToolbarHandler
import androidx.fragment.app.Fragment
import com.sogeti.inno.cherry.comm.CommandBuilder
import com.sogeti.inno.cherry.comm.SpeakCommand
import com.sogeti.inno.cherry.core.ControlServer
import com.sogeti.inno.cherry.utils.IdleHandler

class CalculatorActivityMain: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.calculator_main_layout)
        ToolbarHandler.initToolbar(this)

        initMenuButtons()
        if (savedInstanceState == null) {
            val bundle = Bundle()
            bundle.putInt("difficulty", 1)
            val initialFragment = CalculatorMainFragment()
            initialFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .add(R.id.calculator_fragment_container, initialFragment, "calculator_main")
                .commit()
        }
        ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Bienvenue sur le jeu de calculatrice. Tu peux tenter de réussir les calculs proposés tout en choisissant la difficulté. Tu peux également me demander de faire un calcul, je serais heureuse de te donner la réponse")))
    }

    private fun initMenuButtons() {
        val calculator_poppy = findViewById<Button>(R.id.calculator_speak_btn)
        calculator_poppy.setOnClickListener {
            replaceFragment(PoppyCalculatorFragment(), "poppy_calculator")
        }
        val levelOneBtn = findViewById<Button>(R.id.calculator_level_1)
        levelOneBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("difficulty", 1)
            val calculatorFragment = CalculatorMainFragment()
            calculatorFragment.arguments = bundle
            replaceFragment(calculatorFragment, "calculator_main_level_one")
        }
        val levelTwoBtn = findViewById<Button>(R.id.calculator_level_2)
        levelTwoBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("difficulty", 2)
            val calculatorFragment = CalculatorMainFragment()
            calculatorFragment.arguments = bundle
            replaceFragment(calculatorFragment, "calculator_main_level_two")
        }
        val levelThreeBtn = findViewById<Button>(R.id.calculator_level_3)
        levelThreeBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("difficulty", 3)
            val calculatorFragment = CalculatorMainFragment()
            calculatorFragment.arguments = bundle
            replaceFragment(calculatorFragment, "calculator_main_level_three")
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.calculator_fragment_container, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        IdleHandler.resetCount()
    }

}