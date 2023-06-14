package com.example.fitfume.repository

import com.example.fitfume.network.ApiClient
import com.example.fitfume.network.data.request.LoginRequest
import com.example.fitfume.network.data.request.SignUpRequest
import com.example.fitfume.network.data.response.FindAllPerfumeResponse
import com.example.fitfume.network.data.response.PerfumeResponse
import com.example.fitfume.network.data.response.UserResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.Body

interface UserRepository {
    fun signup(request: SignUpRequest): Single<UserResponse>
    fun login(request: LoginRequest): Single<UserResponse>
}


class UserRepositoryImpl: UserRepository{
    override fun signup(request: SignUpRequest): Single<UserResponse> {
        return ApiClient.api.signup(request)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun login(request: LoginRequest): Single<UserResponse> {
        return ApiClient.api.login(request)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }
}