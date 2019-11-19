package com.sogeti.inno.cherry.utils

import android.util.Log
import com.sogeti.inno.cherry.comm.CommandBuilder
import com.sogeti.inno.cherry.comm.ReviveDialog
import com.sogeti.inno.cherry.comm.TimerReset
import com.sogeti.inno.cherry.core.ControlServer

object IdleHandler {

    var defaultCount = 120
    var count = defaultCount
    init{
        Thread {
            while (true) {
                resetCount(false)
                while(count > 0) {
                    Thread.sleep(1000)
                    count--
                }
                ControlServer.sendCommand(CommandBuilder.toJson(ReviveDialog()))
                Log.d("Idle", "Revive command sent")
            }
        }.start()
//        launchThread()
    }

//    private fun launchThread() {
//
//    }
//

    fun resetCount(sendReset: Boolean = true){
        count = defaultCount
        if (sendReset) {
            ControlServer.sendCommand(CommandBuilder.toJson(TimerReset()))
        }
    }
}