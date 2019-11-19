package com.sogeti.inno.cherry.activities.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.CalculatorGameItem
import com.sogeti.inno.cherry.comm.ChangeEmotion
import com.sogeti.inno.cherry.comm.CommandBuilder
import com.sogeti.inno.cherry.comm.SpeakCommand
import com.sogeti.inno.cherry.core.ControlServer
import com.sogeti.inno.cherry.utils.CalculatorItemsHolder
import com.sogeti.inno.cherry.utils.EmotionsEnum
import es.dmoral.toasty.Toasty
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class CalculatorMainFragment : Fragment() {
    private var difficulty : Int = 0
    private var currentNumber = 0
    private var calculatorItems: ArrayList<CalculatorGameItem> = ArrayList()
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val arg = arguments as Bundle
            difficulty = arg.getInt("difficulty")
            val fullItemsArray: ArrayList<CalculatorGameItem>? = CalculatorItemsHolder.instance.ItemsArray
            if (fullItemsArray == null || fullItemsArray.size == 0) {
                Toasty.warning(requireContext(), "Aucun calcul trouvé", Toast.LENGTH_SHORT, true).show()
            }
            else {
                fullItemsArray.forEach {
                    if (it.Difficulty == difficulty)
                        calculatorItems.add(it)
                }

            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (calculatorItems.size != 0) {
            val view = inflater.inflate(R.layout.calculator_main_fragment_layout, container, false)
            val calculatorView = view.findViewById<TextView>(R.id.calculator_calculation)

            calculatorView.text = calculatorItems[currentIndex].Calculation
            initButtons(view)
            return view
        }
        return null
    }


    private fun initButtons(view: View) {
        val button0 = view.findViewById<Button>(R.id.calculator_0)
        button0.setOnClickListener { updateDigit(0) }
        val button1 = view.findViewById<Button>(R.id.calculator_1)
        button1.setOnClickListener { updateDigit(1) }
        val button2 = view.findViewById<Button>(R.id.calculator_2)
        button2.setOnClickListener { updateDigit(2) }
        val button3 = view.findViewById<Button>(R.id.calculator_3)
        button3.setOnClickListener { updateDigit(3) }
        val button4 = view.findViewById<Button>(R.id.calculator_4)
        button4.setOnClickListener { updateDigit(4) }
        val button5 = view.findViewById<Button>(R.id.calculator_5)
        button5.setOnClickListener { updateDigit(5) }
        val button6 = view.findViewById<Button>(R.id.calculator_6)
        button6.setOnClickListener { updateDigit(6) }
        val button7 = view.findViewById<Button>(R.id.calculator_7)
        button7.setOnClickListener { updateDigit(7) }
        val button8 = view.findViewById<Button>(R.id.calculator_8)
        button8.setOnClickListener { updateDigit(8) }
        val button9 = view.findViewById<Button>(R.id.calculator_9)
        button9.setOnClickListener { updateDigit(9) }
        val computeBtn = view.findViewById<Button>(R.id.calculator_compute)
        computeBtn.setOnClickListener { checkResult() }
        val eraseBtn = view.findViewById<Button>(R.id.calculator_erase)
        eraseBtn.setOnClickListener { resetResult() }
    }

    private fun resetResult() {
        currentNumber = 0
        setText("", false)
    }

    private fun checkResult() {
        if (calculatorItems[currentIndex].Result == currentNumber){
            if (currentIndex + 1 < calculatorItems.size) {
                currentIndex ++
                val calculatorDisplay = view!!.findViewById<TextView>(R.id.calculator_calculation)
                calculatorDisplay.text = calculatorItems[currentIndex].Calculation
                Toasty.success(requireContext(),"Bonne réponse").show()

                ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Bonne réponse")))
                ControlServer.sendCommand(CommandBuilder.toJson(ChangeEmotion(EmotionsEnum.HAPPY.id)))
                resetResult()
                // Wait 500 ms before returning to neutral
                Timer("WaitingChangeEmotion", false).schedule(2000) {
                    ControlServer.sendCommand(CommandBuilder.toJson(ChangeEmotion(EmotionsEnum.NEUTRAL.id)))
                }
                return
            }
            else
                ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Bravo tu as réussi tous les calculs de ce niveau")))
                Toasty.info(requireContext(),"Vous avez fait tous les calculs de ce niveau").show()
                ControlServer.sendCommand(CommandBuilder.toJson(ChangeEmotion(EmotionsEnum.HAPPY.id)))
                return
        }
        else {
            Toasty.error(requireContext(),"Mauvaise réponse").show()
            resetResult()
            ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand("Mauvaise réponse <break time='100ms'>, <emphasis level='reduced'>Essaye encore</emphasis>")))
            ControlServer.sendCommand(CommandBuilder.toJson(ChangeEmotion(EmotionsEnum.SAD.id)))
            // Wait 500 ms before returning to neutral
            Timer("WaitingChangeEmotion", false).schedule(2000) {
                ControlServer.sendCommand(CommandBuilder.toJson(ChangeEmotion(EmotionsEnum.NEUTRAL.id)))
            }
        }
    }

    private fun updateDigit(newValue: Int) {
        currentNumber = currentNumber*10 + newValue
        setText(currentNumber.toString(), true)
    }

    private fun setText(content: String, toAppend: Boolean) {
        val resView = view!!.findViewById<TextView>(R.id.calculator_result)
        when {
            toAppend -> {
                resView.text = content
            }
            else -> resView.text = content
        }

    }

    override fun onDestroy() {
        ControlServer.sendCommand(CommandBuilder.toJson(ChangeEmotion(EmotionsEnum.NEUTRAL.id)))
        super.onDestroy()

    }
}