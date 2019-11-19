package com.sogeti.inno.cherry.activities.models

data class Choregraphy(var name: String, var moves: ArrayList<ChoregraphyMove>, var music: String) {
    var isSaved: Boolean = true
}

data class ChoregraphyMove(val description: String, val imageId: Int, val name: String) {

}