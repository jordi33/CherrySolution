package com.sogeti.inno.cherry.utils

import com.log4k.w
import com.sogeti.inno.cherry.utils.props.ServerProps
import java.lang.Exception
import java.util.*

object PropsLoader{

    fun getPropsFor(name : String): Properties {
        when(name){
            "ServerConnector" -> return ServerProps().properties
            else -> w("Aborting.")
        }
        throw Exception("Trying to get properties for unknown type: $name")
    }
}