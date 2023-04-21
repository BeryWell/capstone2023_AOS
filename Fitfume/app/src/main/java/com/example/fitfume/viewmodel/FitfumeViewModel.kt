package com.example.fitfume.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitfume.model.FitfumeEventBanner
import com.example.fitfume.repository.CampsiteRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FitfumeViewModel : ViewModel() {

    private val campsiteRepositoryImpl = CampsiteRepositoryImpl()

    private val _campsiteEventList: MutableLiveData<List<FitfumeEventBanner>> = MutableLiveData()
    private val _currentPosition: MutableLiveData<Int> = MutableLiveData()

    val campsiteEventList: LiveData<List<FitfumeEventBanner>>
        get() = _campsiteEventList

    val currentPosition: LiveData<Int>
        get() = _currentPosition

    init {
        _currentPosition.value = 0
    }

    fun setCurrentPosition(position: Int) {
        _currentPosition.value = position
    }

    fun getCampsiteEventBannerItems() {
        viewModelScope.launch {
            val campsiteEventBannerItemLiveData =
                campsiteRepositoryImpl.getCampsiteEventBannerItems()
            withContext(Dispatchers.Main) {
                _campsiteEventList.value = campsiteEventBannerItemLiveData
            }
        }
    }

}