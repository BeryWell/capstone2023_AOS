package com.example.fitfume.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitfume.network.data.response.FindAllPerfumeResponse
import com.example.fitfume.repository.PerfumeRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class PerfumeViewModel : ViewModel() {
    private val perfumeRepository = PerfumeRepositoryImpl()

    private var _perfumeInfo = MutableLiveData<List<FindAllPerfumeResponse>>()
    val perfumeInfo: LiveData<List<FindAllPerfumeResponse>>
        get() = _perfumeInfo

    @SuppressLint("CheckResult")
    fun findAllPerfumes(){
        perfumeRepository.findAllPerfumes().subscribeBy (
            onSuccess = {
                _perfumeInfo.value = it
                Log.d("lhj", "findAllPerfume: $it")
            },
            onError = {
                it.printStackTrace()
            })
    }
}