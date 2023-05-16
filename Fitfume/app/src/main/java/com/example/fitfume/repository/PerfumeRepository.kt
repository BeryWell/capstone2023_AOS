package com.example.fitfume.repository

import com.example.fitfume.network.ApiClient
import com.example.fitfume.network.chatgpt.GptApiClient
import com.example.fitfume.network.data.request.GptRequest
import com.example.fitfume.network.data.response.FindAllPerfumeResponse
import com.example.fitfume.network.data.response.GptResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface PerfumeRepository {
    fun findAllPerfumes(): Single<List<FindAllPerfumeResponse>>
}


class PerfumeRepositoryImpl: PerfumeRepository{
    override fun findAllPerfumes(): Single<List<FindAllPerfumeResponse>> {
        return ApiClient.api.findAllPerfumes()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }
}