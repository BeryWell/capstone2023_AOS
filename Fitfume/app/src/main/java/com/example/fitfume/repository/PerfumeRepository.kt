package com.example.fitfume.repository

import com.example.fitfume.network.ApiClient
import com.example.fitfume.network.chatgpt.GptApiClient
import com.example.fitfume.network.data.request.GptRequest
import com.example.fitfume.network.data.response.FindAllPerfumeResponse
import com.example.fitfume.network.data.response.FindPerfumeResponse
import com.example.fitfume.network.data.response.GptResponse
import com.example.fitfume.network.data.response.PerfumeResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.Query

interface PerfumeRepository {
    fun findAllPerfumes(): Single<List<FindAllPerfumeResponse>>
    fun findPerfumeByName(name: String): Single<PerfumeResponse>
}


class PerfumeRepositoryImpl: PerfumeRepository{
    override fun findAllPerfumes(): Single<List<FindAllPerfumeResponse>> {
        return ApiClient.api.findAllPerfumes()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun findPerfumeByName(name: String): Single<PerfumeResponse> {
        return ApiClient.api.findPerfumeByName(name)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }
}