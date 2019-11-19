package com.sogeti.inno.cherry.utils

data class MovementsHolder(var movementList: List<Movement>?) {
    companion object {
        val instance : MovementsHolder = MovementsHolder(null)
    }
}

data class Movement(val Name: String, val Directory: String, val Image: String)