package com.sogeti.inno.cherry.comm

interface Commandable{
    fun onReceiveCommand(command: Command)
}