package com.ash.smplaqi.network

import android.util.Log
import com.ash.smplaqi.WS_URL
import com.ash.smplaqi.data.CityAqi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class WebSocketServiceImpl constructor(private val onMessageReceived: (List<CityAqi>) -> Unit) :
    WebSocketListener(), WebSocketService {

    private val ws: WebSocket
    init {
        val okHttpClient = OkHttpClient.Builder()
            .pingInterval(5, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url(WS_URL)
            .build()

        ws = okHttpClient.newWebSocket(request, this)

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        okHttpClient.dispatcher.executorService.shutdown()
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d(TAG, "onOpen() : response : $response")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d(TAG, "onMessage() : text : $text")
        try {
            val jsonArray = JSONArray(text)

            val list = mutableListOf<CityAqi>()
            for (i in 0 until jsonArray.length()) {
                val cityAqi = Json.decodeFromString<CityAqi>(jsonArray[i].toString())
                list.add(cityAqi)
            }
            onMessageReceived(list)
        } catch (e: JSONException) {
            Log.d(TAG, "onMessage() : JSONException : ${e.message}")
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d(TAG, "onClosing() : code : $code")
        Log.d(TAG, "onClosing() : reason : $reason")
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d(TAG, "onFailure() : Throwable : ${t.message}")
    }

    override fun connect() {
        Log.d(TAG, "connect()")
    }

    override fun send(message: JSONObject?) {
        val packet = message.toString()
        ws.send(packet)
        Log.d(TAG, "send() : message :$message")
    }

    override fun cancel() {
        ws.cancel()
        Log.d(TAG, "cancel()")
    }

    companion object {
        private const val TAG = "WebSocketServiceImpl"
        private const val NORMAL_CLOSURE_STATUS = 1000
    }
}