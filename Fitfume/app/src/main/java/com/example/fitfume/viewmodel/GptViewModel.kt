package com.example.fitfume.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitfume.network.data.request.GptRequest
import com.example.fitfume.repository.GptRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class GptViewModel : ViewModel(){
    private val gptRepository = GptRepositoryImpl()

    private var _gptText = MutableLiveData<String>()
    val gptText: LiveData<String>
        get() = _gptText


    private var _recommendTextArr = MutableLiveData<MutableList<String>>()
    val recommendTextArr: LiveData<MutableList<String>>
        get() = _recommendTextArr

    private var _recommendStateText = MutableLiveData<String>()
    val recommendStateText: LiveData<String>
        get() = _recommendStateText

    init{
        _recommendTextArr.value = mutableListOf()
        _recommendStateText.value = ""
    }

    @SuppressLint("CheckResult")
    fun getChatAnswer(request: GptRequest){
        gptRepository.getChatAnswer(request).subscribeBy (
            onSuccess = {
                _gptText.value = it.choices[0].message.content
                Log.d("lhj", "getChatAnswer: $it")
            },
            onError = {
                it.printStackTrace()
            })
    }

    fun updateRecommendTextArr(str: String){
        _recommendTextArr.value!!.add(str)
    }

    fun deleteRecommendTextArr(){
        _recommendTextArr.value!!.removeAt(_recommendTextArr.value!!.size - 1)
    }

    fun resetRecommendTextArr(){
        _recommendTextArr.value!!.clear()
    }

    fun updateRecommendStateText(value: String){
        _recommendStateText.value = value
    }

}
