package com.sogeti.inno.cherry.utils

import com.sogeti.inno.cherry.activities.models.CalculatorGameItem

data class CalculatorItemsHolder (var ItemsArray: ArrayList<CalculatorGameItem>){
    companion object {
        val instance = CalculatorItemsHolder(ArrayList())
    }

    init {

    }

}