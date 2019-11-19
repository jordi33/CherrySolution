package com.sogeti.inno.cherry.activities.ui.wordImages

import android.widget.LinearLayout
import com.log4k.w
import com.sogeti.inno.cherry.R
import kotlin.random.Random

class AvailableArea(val context: WordImagesActivityMain) {


    private val alphabet = "abcdefghijklmnopqrstuvxyz"
    private val nbColumns = 5
    private val nbLines = 2
    private val firstLine = context.findViewById<LinearLayout>(R.id.lettersLine1)
    private val secondLine = context.findViewById<LinearLayout>(R.id.lettersLine2)
    private val BUTTON_SIZE = 70f

    init {
        initLines()
        insertWord()
    }

    private fun initLines() {

        firstLine.removeAllViews()
        secondLine.removeAllViews()
        for (i in 0 until nbColumns) {
            val lb1 = LetterButton(context, i, 0,BUTTON_SIZE, randomLetter())
            val lb2 = LetterButton(context, i, 1,BUTTON_SIZE,randomLetter())
            firstLine.addView(lb1)
            secondLine.addView(lb2)
        }
    }

    private fun insertWord() {
        val nbSlots = nbColumns * nbLines
        val freeSlots = MutableList(nbSlots){it}
        Level.current.word.forEach {
            val rand = freeSlots[Random.nextInt(freeSlots.size-1)]
            freeSlots.remove(rand)
            val rndx = rand%nbColumns
            val rndy = rand/nbColumns

            if (rndy == 0) {
                firstLine.removeViewAt(rndx)
                firstLine.addView(LetterButton(context,rndx,rndy, BUTTON_SIZE, it.toString()), rndx)
            } else {
                secondLine.removeViewAt(rndx)
                secondLine.addView(LetterButton(context,rndx,rndy, BUTTON_SIZE, it.toString()), rndx)
            }
        }
    }

    fun randomLetter() : String {
        return alphabet[Random.nextInt(alphabet.length)].toString()
    }


    fun addLetter(lb: LetterButton): LetterButton? {
        val line = if( lb.y == 0) firstLine else secondLine
        val removed = line.getChildAt(lb.x)
        line.removeViewAt(lb.x)
        line.addView(lb, lb.x)
        lb.chosen = false
        lb.setDipSize(BUTTON_SIZE)
        return removed as LetterButton
    }


    fun removeLetter(lb: LetterButton) {
        (lb.parent as LinearLayout).removeView(lb)
        val line = if( lb.y == 0) firstLine else secondLine
        line.addView(LetterButton(context,BUTTON_SIZE),lb.x)
    }



}