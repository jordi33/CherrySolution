package com.sogeti.inno.cherry.core

interface IConnectionListener {
    fun onMessage(text: String)
    fun onClose()
    fun onOpen()

}
