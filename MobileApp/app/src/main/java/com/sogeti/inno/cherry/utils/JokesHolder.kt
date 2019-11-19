package com.sogeti.inno.cherry.utils

class JokesHolder(var jokesList: List<String>?) {
    companion object {
        val instance : JokesHolder = JokesHolder(null)
    }
}