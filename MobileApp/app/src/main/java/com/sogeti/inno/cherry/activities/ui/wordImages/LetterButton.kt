package com.sogeti.inno.cherry.activities.ui.wordImages

import android.annotation.SuppressLint
import android.app.ActionBar
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout.LayoutParams
@SuppressLint("ViewConstructor")
class LetterButton(
    private val context: WordImagesActivityMain,
    val x : Int,
    val y : Int,
    val size : Float,
    val letter: String
) : ImageButton(context) {

    var chosen = false

    constructor(context: WordImagesActivityMain, size: Float): this(context, -1,-1,size,"empty")

    init {
        this.layout(0, 0, 0, 0)
        this.setImageDrawable(context.getImageFromName(letter))
        this.layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT)
        this.setDipSize(size)
        this.adjustViewBounds = true
        this.scaleType = ScaleType.CENTER_INSIDE
        this.setOnClickListener {
            if( !isEmpty()){
                if (!chosen){
                    context.choseLetter(this)
                } else {
                    context.unChoseLetter(this)
                }
            }
        }
    }

    fun isEmpty() : Boolean {
       return  letter == "empty"
    }

    fun setDipSize( size : Float ){
        val dm = context.resources.displayMetrics
        val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,size,dm)
        this.layoutParams.width = width.toInt()
    }

}

