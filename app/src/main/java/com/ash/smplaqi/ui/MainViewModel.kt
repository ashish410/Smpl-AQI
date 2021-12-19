package com.ash.smplaqi.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ash.smplaqi.data.model.CityAqi
import com.ash.smplaqi.repository.AQIRepository
import com.ash.smplaqi.repository.FailConnectionListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val aqiRepository: AQIRepository) : ViewModel() {

    var selectedCityLiveData = MutableLiveData<String>()

    fun getLatestData(): Flow<List<CityAqi>> = aqiRepository.getLatestData()

    fun getSelectedCityData(): Flow<List<CityAqi>>? {
        val city = selectedCityLiveData.value ?: return null
        return aqiRepository.getLastValuesOf(city)
    }

    fun connect() {
        aqiRepository.connect()
    }

    fun cancel() {
        aqiRepository.cancel()
    }

    fun setConnectionFailureListener(failConnectionListener: FailConnectionListener?){
        aqiRepository.failConnectionListener = failConnectionListener
    }
}