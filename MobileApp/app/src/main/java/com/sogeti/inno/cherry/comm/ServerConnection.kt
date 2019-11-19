package com.sogeti.inno.cherry.comm

import com.bumptech.glide.Glide.init
import com.log4k.e
import com.log4k.i
import com.sogeti.inno.cherry.core.IConnectionListener
import com.sogeti.inno.cherry.utils.PropsLoader
import okhttp3.*
import okio.ByteString
import java.util.*

class ServerConnector(l: IConnectionListener) : WebSocketListener(){
    private var properties = Properties()
    private var client: OkHttpClient? = null
    private var ws: WebSocket? = null
    private var listener: IConnectionListener
    private var connecting: Boolean = false

    init {
        // The properties are in the assets/ServerProps.properties file
        properties = PropsLoader.getPropsFor(this::class.simpleName.toString())
        i("Load ServerConnector properties: OK")
        this.client = OkHttpClient()
        this.listener = l
    }

    fun connect(): Boolean {
        if (! connecting) {
            try {

                val ip = properties.getProperty("ip")
                val port = properties.getProperty("port")
                val resource = properties.getProperty("resource")
                val request = Request.Builder().url("ws://$ip:$port/$resource").build()
                this.ws = client!!.newWebSocket(request, this)
                i("Trying to connect to WebSocketServer")
                connecting = true
                return true
            }catch (ex : Exception){
                e("WebSocketServer connection failed.:\n${ex.message}  \n ${ex.stackTrace}")
                return false
            }
        }
        return true
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        this.listener.onMessage(text)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        this.listener.onClose()
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        i("Connected to WebSocketServer")
        this.listener.onOpen()
    }

    fun send(text: String) {
        this.ws!!.send(text)
    }
}