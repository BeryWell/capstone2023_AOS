package com.example.fitfume.network

import com.example.fitfume.network.data.request.CreateReviewRequest
import com.example.fitfume.network.data.request.GptRequest
import com.example.fitfume.network.data.request.LoginRequest
import com.example.fitfume.network.data.request.SignUpRequest
import com.example.fitfume.network.data.response.*
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

    @POST("/api/v1/auth/register")
    fun signup(@Body request: SignUpRequest): Single<UserResponse>

    @POST("/api/v1/auth/login")
    fun login(@Body request: LoginRequest): Single<UserResponse>

    @POST("/api/review/create")
    fun createReview(@Body request: CreateReviewRequest): Single<CreateReviewResponse>

    @GET("/api/review/getall/{perfumeId}")
    fun getAllReviewByPerfumeId(@Path("perfumeId") perfumeId: Int): Single<List<GetAllPerfumeResponse>>
}
