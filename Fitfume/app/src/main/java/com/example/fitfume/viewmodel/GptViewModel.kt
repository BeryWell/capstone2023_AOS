package com.example.fitfume.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fitfume.network.data.request.GptRequest
import com.example.fitfume.repository.GptRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class GptViewModel : ViewModel(){
    private val gptRepository = GptRepositoryImpl()


    @SuppressLint("CheckResult")
    fun getChatAnswer(request: GptRequest){
        gptRepository.getChatAnswer(request).subscribeBy (
            onSuccess = {
                Log.d("lhj", "getChatAnswer: $it")
            },
            onError = {
                it.printStackTrace()
            })
    }
}
