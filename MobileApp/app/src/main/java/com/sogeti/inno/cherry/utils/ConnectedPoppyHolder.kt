package com.sogeti.inno.cherry.utils

data class ConnectedPoppyHolder(var connectedPoppies: List<String>?) {
    companion object {
        val instance : ConnectedPoppyHolder = ConnectedPoppyHolder(null)
        var connected : Boolean = false
    }
}