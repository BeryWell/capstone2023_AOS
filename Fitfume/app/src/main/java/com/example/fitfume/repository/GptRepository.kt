package com.example.fitfume.repository

import com.example.fitfume.network.ApiClient
import com.example.fitfume.network.GptApiClient
import com.example.fitfume.network.data.request.GptRequest
import com.example.fitfume.network.data.response.GptResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GptRepository {
    fun getChatAnswer(request: GptRequest): Single<GptResponse>
}

class GptRepositoryImpl: GptRepository{
    override fun getChatAnswer(request: GptRequest): Single<GptResponse> {
        return GptApiClient.api.getChatAnswer(request)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

}