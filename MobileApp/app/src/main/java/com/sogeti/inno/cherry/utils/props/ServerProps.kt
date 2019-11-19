package com.sogeti.inno.cherry.utils.props

import com.log4k.e
import com.sogeti.inno.cherry.utils.Assets
import java.util.*

class ServerProps {

    var properties : Properties = Properties()
        private set

    init {
        this.loadPropsFromProps()
    }

    private fun loadPropsFromProps() {
        try {
            val inStream = Assets.CherryAssets!!.open("ServerProps.properties")
            properties.load(inStream)
        }catch (e: Exception){
            e(" *** Exception!! Cannot load ServerProps from file *** \t ${e.message} \t ${e.stackTrace} ")
        }
    }
}