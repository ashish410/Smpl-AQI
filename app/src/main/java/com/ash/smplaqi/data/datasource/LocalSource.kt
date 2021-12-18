package com.ash.smplaqi.data.datasource

import com.ash.smplaqi.data.model.CityAqi
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    fun insertData(dataList: List<CityAqi>)
    fun getLatestData(): Flow<List<CityAqi>>
    fun getLastValuesOf(city: String): Flow<List<CityAqi>>
}