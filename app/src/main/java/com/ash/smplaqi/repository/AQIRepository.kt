package com.ash.smplaqi.repository

import com.ash.smplaqi.data.datasource.LocalSource
import com.ash.smplaqi.data.datasource.RemoteSource
import com.ash.smplaqi.data.db.CityAqiDao
import com.ash.smplaqi.data.model.CityAqi
import javax.inject.Inject

class AQIRepository @Inject constructor(
    private val cityAqiDao: CityAqiDao,
    private val webSocketImpl: WebSocketImpl
) : RemoteSource, LocalSource, WebSocketResponseListener {

    init {
        webSocketImpl.setResponseListener(this)
    }

    var failConnectionListener: FailConnectionListener? = null

    override fun connect() {
        webSocketImpl.connect()
    }

    override fun cancel() {
        webSocketImpl.cancel()
    }

    override fun insertData(dataList: List<CityAqi>) {
        cityAqiDao.insertAll(dataList)
    }

    override fun getLastValuesOf(city: String) = cityAqiDao.getLastValuesOf(city)
    override fun getLatestData() = cityAqiDao.getLastValues()
    override fun onSuccess(list: List<CityAqi>) {
        insertData(list.onEach {
            it.seconds = System.currentTimeMillis() / 1000
        })
    }

    override fun onFailure() {
        failConnectionListener?.onSocketFailure()
    }
}