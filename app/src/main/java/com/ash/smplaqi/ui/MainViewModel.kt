package com.ash.smplaqi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ash.smplaqi.data.CityAqi
import com.ash.smplaqi.network.WebSocketServiceImpl
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val mutableLiveData = MutableLiveData<List<CityAqi>>()
    val liveData: LiveData<List<CityAqi>> = mutableLiveData

    private val webSocketServiceImpl: WebSocketServiceImpl = WebSocketServiceImpl {
        viewModelScope.launch {
            mutableLiveData.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        webSocketServiceImpl.cancel()
    }
}