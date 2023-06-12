package com.example.fitfume.network

import com.example.fitfume.network.data.request.GptRequest
import com.example.fitfume.network.data.response.FindAllPerfumeResponse
import com.example.fitfume.network.data.response.FindPerfumeResponse
import com.example.fitfume.network.data.response.GptResponse
import com.example.fitfume.network.data.response.PerfumeResponse
import io.reactivex.Single
import retrofit2.http.*

interface  ApiInterface {
    //ChatGPT POST
    @POST("chat/completions")
    fun getChatAnswer(@Body request: GptRequest): Single<GptResponse>

    // perfume find all
    @GET("/api/perfume/findAll")
    fun findAllPerfumes(): Single<List<FindAllPerfumeResponse>>

    @GET("/api/perfume/get_perfumes")
    fun findPerfumeByName(@Query("name") name: String): Single<PerfumeResponse>
}
