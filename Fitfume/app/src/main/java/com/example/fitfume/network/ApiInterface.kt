package com.example.fitfume.network

import com.example.fitfume.network.data.request.GptRequest
import com.example.fitfume.network.data.response.FindAllPerfumeResponse
import com.example.fitfume.network.data.response.GptResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    //ChatGPT POST
    @POST("chat/completions")
    fun getChatAnswer(@Body request: GptRequest): Single<GptResponse>

    // perfume find all
    @GET("/api/perfume/findAll")
    fun findAllPerfumes(): Single<List<FindAllPerfumeResponse>>
}
