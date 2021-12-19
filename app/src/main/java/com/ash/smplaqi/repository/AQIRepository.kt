package com.ash.smplaqi.repository

import android.util.Log
import com.ash.smplaqi.data.datasource.LocalSource
import com.ash.smplaqi.data.datasource.RemoteSource
import com.ash.smplaqi.data.db.CityAqiDao
import com.ash.smplaqi.data.model.CityAqi
import com.google.gson.Gson
import okhttp3.*
import java.lang.reflect.Type
import javax.inject.Inject

class AQIRepository @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val cityAqiDao: CityAqiDao,
    private val request: Request,
    private val gson: Gson,
    private val type: Type
) : RemoteSource, LocalSource {

    companion object {
        const val TAG = "AQIRepository"
    }

    var failConnectionListener: FailConnectionListener? = null
    private var webSocket: WebSocket? = null

    private val listener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Log.d(TAG, "onMessage() : text : $text")
            val list = gson.fromJson<List<CityAqi>>(text, type)

            insertData(list.onEach {
                it.seconds = System.currentTimeMillis() / 1000
            })
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            failConnectionListener?.onConnectionFailed()
        }
    }

    override fun connect() {
        webSocket = okHttpClient.newWebSocket(request, listener)
    }

    fun cancel() {
        webSocket?.cancel()
    }

    override fun insertData(dataList: List<CityAqi>) {
        cityAqiDao.insertAll(dataList)
    }

    override fun getLastValuesOf(city: String) = cityAqiDao.getLastValuesOf(city)
    override fun getLatestData() = cityAqiDao.getLastValues()
}