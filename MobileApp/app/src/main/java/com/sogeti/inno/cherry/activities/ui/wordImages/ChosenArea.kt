package com.sogeti.inno.cherry.activities.ui.wordImages

import android.widget.LinearLayout
import com.sogeti.inno.cherry.R

class ChosenArea(private val context: WordImagesActivityMain) {

    private val container = context.findViewById<LinearLayout>(R.id.choices)
    private var index = -1
    private val BUTTON_SIZE = 50f

    init {
        container.removeAllViews()
        for (i in 0 until Level.current.length) {
            container.addView(LetterButton(context,BUTTON_SIZE))
        }
    }

    fun addLetter(lb: LetterButton) {
        index++
        container.removeViewAt(index)
        container.addView(lb, index)
        lb.chosen = true
        lb.setDipSize(BUTTON_SIZE)
    }

    fun removeLetter(lb: LetterButton) {
        container.removeView(lb)
        container.addView(LetterButton(context,BUTTON_SIZE), index--)
    }

    fun isFull() = index == (Level.current.length - 1)

    fun getChosenWord(): String {
        return Array(index + 1) { container.getChildAt(it)}
            .map { ( it as LetterButton).letter }
            .foldRight(""){s, acc -> s+ acc  }
    }

}