package com.example.fitfume.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitfume.network.data.request.CreateReviewRequest
import com.example.fitfume.network.data.request.SignUpRequest
import com.example.fitfume.network.data.response.CreateReviewResponse
import com.example.fitfume.network.data.response.GetAllPerfumeResponse
import com.example.fitfume.repository.ReviewRepository
import com.example.fitfume.repository.ReviewRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class ReviewViewModel : ViewModel() {
    private val reviewRepository = ReviewRepositoryImpl()

    private var _getAllPerfumeReview = MutableLiveData<List<GetAllPerfumeResponse>>()
    val getAllPerfumeReview: LiveData<List<GetAllPerfumeResponse>>
        get() = _getAllPerfumeReview


    @SuppressLint("CheckResult")
    fun createReview(request: CreateReviewRequest){
        reviewRepository.createReview(request).subscribeBy (
            onSuccess = {
                Log.d("successlhj", "success: $it")
            },
            onError = {
                it.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    fun getAllReviewByPerfumeId(perfumeId: Int){
        reviewRepository.getAllReviewByPerfumeId(perfumeId).subscribeBy (
            onSuccess = {
                _getAllPerfumeReview.value = it
                Log.d("lhj", "getAllReviewByPerfumeId: $it")
            },
            onError = {
                it.printStackTrace()
            })
    }
}