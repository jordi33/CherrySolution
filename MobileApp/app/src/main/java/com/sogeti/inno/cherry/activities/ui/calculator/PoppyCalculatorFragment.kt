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
import com.sogeti.inno.cherry.comm.CommandBuilder
import com.sogeti.inno.cherry.comm.SpeakCommand
import com.sogeti.inno.cherry.core.ControlServer
import es.dmoral.toasty.Toasty

class PoppyCalculatorFragment: Fragment() {
    private var x1: Int = 0
    private var x2: Int = 0
    private var op: ((Int, Int) -> Any)? = null
    private var isFirstDigit: Boolean = true
    private var isDividingByZero: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calculator_poppy_fragment_layout, container, false)
        initButtons(view)
        return view
    }

    private fun addition(x: Int, y: Int): Int {
        return x + y
    }

    private fun subtraction(x: Int, y: Int): Int {
        return x - y
    }

    private fun multiplication(x: Int, y: Int): Int {
        return x * y
    }

    private fun division(x: Int, y: Int): Float {
        if (y == 0) {
            isDividingByZero = true
            return 0.toFloat()
        } else
            return (x / y).toFloat()
    }

    private fun initButtons(view: View) {
        val button0 = view.findViewById<Button>(R.id.poppy_speak_zero)
        button0.setOnClickListener { updateDigit(0) }
        val button1 = view.findViewById<Button>(R.id.poppy_speak_one)
        button1.setOnClickListener { updateDigit(1) }
        val button2 = view.findViewById<Button>(R.id.poppy_speak_two)
        button2.setOnClickListener { updateDigit(2) }
        val button3 = view.findViewById<Button>(R.id.poppy_speak_three)
        button3.setOnClickListener { updateDigit(3) }
        val button4 = view.findViewById<Button>(R.id.poppy_speak_four)
        button4.setOnClickListener { updateDigit(4) }
        val button5 = view.findViewById<Button>(R.id.poppy_speak_five)
        button5.setOnClickListener { updateDigit(5) }
        val button6 = view.findViewById<Button>(R.id.poppy_speak_six)
        button6.setOnClickListener { updateDigit(6) }
        val button7 = view.findViewById<Button>(R.id.poppy_speak_seven)
        button7.setOnClickListener { updateDigit(7) }
        val button8 = view.findViewById<Button>(R.id.poppy_speak_eight)
        button8.setOnClickListener { updateDigit(8) }
        val button9 = view.findViewById<Button>(R.id.poppy_speak_nine)
        button9.setOnClickListener { updateDigit(9) }
        val button_plus = view.findViewById<Button>(R.id.poppy_speak_plus)
        button_plus.setOnClickListener { updateOperator(::addition, " + ") }
        val button_minus = view.findViewById<Button>(R.id.poppy_speak_minus)
        button_minus.setOnClickListener {updateOperator(::subtraction, " - ") }
        val button_mult = view.findViewById<Button>(R.id.poppy_speak_multiply)
        button_mult.setOnClickListener {updateOperator(::multiplication, " * ") }
        val button_divide = view.findViewById<Button>(R.id.poppy_speak_divide)
        button_divide.setOnClickListener {updateOperator(::division, " / ") }
        val button_res = view.findViewById<Button>(R.id.poppy_speak_result_button)
        button_res.setOnClickListener { printRes() }
        val button_erase = view.findViewById<Button>(R.id.poppy_speak_erase_button)
        button_erase.setOnClickListener { eraseRes() }

    }

    private fun updateOperator(operator: (Int, Int) -> Any, operatorString: String) {
        op = operator
        setText(operatorString, true)
        isFirstDigit = false
    }

    private fun updateDigit(digit: Int) {
        if (isFirstDigit)
            x1 = x1 * 10 + digit
        else
            x2 = x2 * 10 + digit
        setText(digit.toString(), true)
    }

    private fun printRes() {
        if (! isFirstDigit) {
                val res = op?.invoke(x1,x2)
            if (! isDividingByZero) {
                setText(res.toString(), false)
                ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand(buildTextPoppy(res.toString()))))
            }
            else{
                setText("Infini", false)
                ControlServer.sendCommand(CommandBuilder.toJson(SpeakCommand(buildTextPoppy("Infini"))))
            }
        }
        else {
            Toasty.warning(requireContext(), "Veuillez renseigner un chiffre en premier", Toast.LENGTH_LONG, true).show()
        }
    }

    private fun eraseRes() {
        x1 = 0
        x2 = 0
        op = null
        isFirstDigit = true
        setText("", false)
    }

    private fun setText(content: String, toAppend: Boolean) {
        val resView = view!!.findViewById<TextView>(R.id.poppy_speak_result_text)
        when {
            toAppend -> {
                val newText = "${resView.text}$content"
                resView.text = newText
            }
            else -> resView.text = content
        }
    }

    private fun buildTextPoppy(result: String): String {
        return when(op) {
            ::addition -> "$x1 plus $x2 vaut $result"
            ::subtraction -> "$x1 moins $x2 vaut $result"
            ::multiplication -> "$x1 multiplié par $x2 vaut $result"
            else -> {
                if (!isDividingByZero)
                    "$x1 divisé par $x2 vaut <say-as interpret-as='fraction'>$x1/$x2</say-as>"
                else
                    "$x1 divisé par $x2 vaut l'infini"
            }
        }
    }
}