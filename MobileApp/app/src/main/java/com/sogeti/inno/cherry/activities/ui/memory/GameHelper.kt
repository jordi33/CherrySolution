package com.sogeti.inno.cherry.activities.ui.memory


class GameHelper(val gameActivity: MemoryActivityMain) {

    var attemptCounter = 0
    var secondsPassed = 0
    var isFinished = false

    init {
        //setCounter()
    }

    fun raiseAttemptCounter() {
        attemptCounter++
    }

    private fun setCounter() {
        Thread {
            while (!isFinished) {
                Thread.sleep(1000)
                secondsPassed++
                gameActivity.runOnUiThread { gameActivity.updateCounterText(secondsPassed.toString()) }
            }
        }.start()
    }
}