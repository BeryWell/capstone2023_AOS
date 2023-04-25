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


    private var _recommendText = MutableLiveData<String>()
    val recommendText: LiveData<String>
        get() = _recommendText


    init{
        _recommendText.value = ""
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

    fun updateRecommendText(str: String){
        _recommendText.value = str
    }

}
