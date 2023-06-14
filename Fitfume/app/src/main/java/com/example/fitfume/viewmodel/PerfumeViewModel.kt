package com.example.fitfume.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitfume.network.data.response.FindAllPerfumeResponse
import com.example.fitfume.network.data.response.FindPerfumeResponse
import com.example.fitfume.repository.PerfumeRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class PerfumeViewModel : ViewModel() {
    private val perfumeRepository = PerfumeRepositoryImpl()

    private var _perfumeInfo = MutableLiveData<List<FindAllPerfumeResponse>>()
    val perfumeInfo: LiveData<List<FindAllPerfumeResponse>>
        get() = _perfumeInfo

    private var _perfumeByName = MutableLiveData<FindPerfumeResponse>()
    val perfumeByName: LiveData<FindPerfumeResponse>
        get() = _perfumeByName

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

    @SuppressLint("CheckResult")
    fun findPerfumeByName(name: String){
        perfumeRepository.findPerfumeByName(name).subscribeBy(
            onSuccess = {
                _perfumeByName.value = it.data.get(0)
                Log.d("lhj", "findPerfumeByName: ${_perfumeByName.value}")
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

}