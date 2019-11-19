package com.sogeti.inno.cherry.utils

import com.sogeti.inno.cherry.activities.models.Tale

class TalesHolder (var talesList: ArrayList<Tale>){
    companion object {
        val instance = TalesHolder(ArrayList())
    }
}