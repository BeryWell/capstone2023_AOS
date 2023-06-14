package com.example.fitfume.repository

import com.example.fitfume.network.ApiClient
import com.example.fitfume.network.data.request.CreateReviewRequest
import com.example.fitfume.network.data.response.CreateReviewResponse
import com.example.fitfume.network.data.response.GetAllPerfumeResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.Body
import retrofit2.http.Path

interface ReviewRepository {
    fun createReview(request: CreateReviewRequest): Single<CreateReviewResponse>
    fun getAllReviewByPerfumeId(perfumeId: Int): Single<List<GetAllPerfumeResponse>>
}

class ReviewRepositoryImpl: ReviewRepository{
    override fun createReview(request: CreateReviewRequest): Single<CreateReviewResponse> {
        return ApiClient.api.createReview(request)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun getAllReviewByPerfumeId(perfumeId: Int): Single<List<GetAllPerfumeResponse>> {
        return ApiClient.api.getAllReviewByPerfumeId(perfumeId)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

}