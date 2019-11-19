package com.sogeti.inno.cherry.activities.ui.wordImages

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import com.sogeti.inno.cherry.R
import kotlinx.android.synthetic.*
import kotlin.random.Random

class FourImagesOneWord : AppCompatActivity() {

    var word = "PIKA"
    val alphabet  = "abcdefghijklmnopqrstuvxyz"
    val lettersMap = HashMap<Char, Drawable>()
    var possibleChoices : Array<ImageButton>


    init {
        val firstLine = Array(5){findViewById<LinearLayout>(R.id.lettersLine1).getChildAt(it) as ImageButton}
        val secondLine = Array(5){findViewById<LinearLayout>(R.id.lettersLine2).getChildAt(it)  as ImageButton}
        possibleChoices = firstLine + secondLine
        for (c in alphabet) {
            lettersMap[c] = getImageFromName(c.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_four_images_one_word)
        initChoices()
        shuffleLetters()
    }


    fun initChoices(){
        val choices = findViewById<LinearLayout>(R.id.choices)
        choices.removeAllViews()

        for( i in 0 until  word.length){
            val imageButton = ImageButton(this)
            imageButton.layout(5,5,5,5)
            imageButton.setImageDrawable( getImageFromName("A"))
        }
    }

    fun shuffleLetters() {
        possibleChoices.forEach {
            it.setImageDrawable(getImageFromName(randomLetter().toString()))
        }

        for( c in word){
            possibleChoices[Random.nextInt(10)].setImageDrawable(getImageFromName(c.toString()))
        }
    }


    fun randomLetter() = alphabet[Random.nextInt(alphabet.length)]

    fun getImageFromName(name : String) : Drawable{
        return  getDrawable(resources.getIdentifier(name, "drawable", packageName))!!
    }

}
