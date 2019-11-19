package com.sogeti.inno.cherry.activities.ui.wordImages

import android.graphics.drawable.Drawable

class Level() {

    lateinit var word : String
        private set
    lateinit var image : Drawable
        private set

    private constructor( word : String) : this() {
        this.word = word
    }

    companion object {
        val levels = listOf(
            Level("souris"),
            Level("bouchon"),
            Level("sauter"),
            Level("boire"),
            Level("croix"),
            Level("corde"),
            Level("noir"),
            Level("neuf"),
            Level("terre"),
            Level("bouton"),
            Level("lourd"),
            Level("rond")
        )

        var currentIndex = 0
            private set
        var current   = levels[currentIndex]
            get() = levels[currentIndex]
            private set

        fun next(){
            currentIndex++
        }

        fun hasNext() : Boolean{
            return currentIndex + 1 < levels.size
        }

        fun loadAll(context : WordImagesActivityMain){
            levels.forEach { it.image = context.getImageFromName(it.word) }
        }
    }

    val length: Int
        get() = word.length

    fun check(word: String) = word == this.word


}

