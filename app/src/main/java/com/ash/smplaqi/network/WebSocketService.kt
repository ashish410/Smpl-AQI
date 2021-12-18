package com.ash.smplaqi.network

import org.json.JSONObject

interface WebSocketService {
    fun connect()
    fun send(message: JSONObject?)
    fun cancel()
}