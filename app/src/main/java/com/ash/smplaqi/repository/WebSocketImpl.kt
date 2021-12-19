package com.ash.smplaqi.repository

import android.util.Log
import com.ash.smplaqi.data.model.CityAqi
import com.google.gson.Gson
import okhttp3.*
import java.lang.reflect.Type
import javax.inject.Inject

class WebSocketImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val request: Request,
    private val gson: Gson,
    private val type: Type
) : WebSocketListener() {

    companion object {
        const val TAG = "WebSocketImpl"
    }

    private var webSocket: WebSocket? = null
    private var webSocketResponseListener: WebSocketResponseListener? = null

    fun setResponseListener(webSocketResponseListener: WebSocketResponseListener) {
        this.webSocketResponseListener = webSocketResponseListener
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d(TAG, "onMessage() : text : $text")
        val list = gson.fromJson<List<CityAqi>>(text, type)
        webSocketResponseListener?.onSuccess(list)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        webSocketResponseListener?.onFailure()
    }

    fun connect() {
        webSocket = okHttpClient.newWebSocket(request, this)
    }

    fun cancel() {
        webSocket?.cancel()
    }
}