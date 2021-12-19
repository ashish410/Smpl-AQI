package com.ash.smplaqi.repository

import com.ash.smplaqi.data.model.CityAqi

interface WebSocketResponseListener {
    fun onSuccess(list: List<CityAqi>)
    fun onFailure()
}