package com.sogeti.inno.cherry.utils

import android.util.Log
import com.sogeti.inno.cherry.R
import com.sogeti.inno.cherry.activities.models.Choregraphy
import com.sogeti.inno.cherry.activities.models.ChoregraphyMove
import com.sogeti.inno.cherry.comm.ServerSideChoregraphy

data class ChoregraphiesHolder(var choregraphiesList: ArrayList<Choregraphy>) {

    companion object {
        val instance: ChoregraphiesHolder = ChoregraphiesHolder(ArrayList())
    }

    init {

    }

    fun initChoregraphyMoves(choregraphyList :List<ServerSideChoregraphy>) {
        if (instance.choregraphiesList.size != 0)
            instance.choregraphiesList = ArrayList()
        for (serverSideChore in choregraphyList) {
            val choregraphyList = ArrayList<ChoregraphyMove>()
            for (movement in serverSideChore.Movements) {
                choregraphyList.add(ChoregraphyMove(getDescription(movement), getImage(movement), movement))
            }
            instance.choregraphiesList.add(Choregraphy(serverSideChore.Name, choregraphyList, serverSideChore.Music))
        }
        Log.d("ChoregraphyHolder", "Choregraphies initialized")
    }

    private fun getImage(moveName: String): Int {
        return when (moveName) {
            "dab" -> R.drawable.dab
            "twist" -> R.drawable.twist
            "balancing" -> R.drawable.balancing
            "shaking" -> R.drawable.shaking
            "r_arm_fwd" -> R.drawable.r_arm_fwd
            "l_arm_fwd" -> R.drawable.l_arm_fwd
            "r_arm_bwd" -> R.drawable.r_arm_back
            "l_arm_bwd" -> R.drawable.l_arm_back
            "r_elbow_up" -> R.drawable.r_arm_up
            "l_elbow_up" -> R.drawable.l_arm_up
            else -> {
                Log.d("Choregraphy Holder", "Image not found for $moveName")
                return 0
            }
        }
    }

    private fun getDescription(moveName: String): String {
        return when (moveName) {
            "dab" -> "Dab"
            "twist" -> "Twist"
            "balancing" -> "Balancing"
            "shaking" -> "Shaking"
            "r_arm_fwd" -> "Bras droit en avant"
            "l_arm_fwd" -> "Bras gauche en avant"
            "r_arm_bwd" -> "Bras droit vers l'arrière"
            "l_arm_bwd" -> "Bras gauche vers l'arrière"
            "r_elbow_up" -> "Bras droit vers le haut"
            "l_elbow_up" -> "Bras gauche vers le haut"
            else -> {
                Log.d("Choregraphy Holder", "Descriptionnot found for $moveName")
                return ""
            }
        }
    }
}