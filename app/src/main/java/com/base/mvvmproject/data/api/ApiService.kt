package com.base.mvvmproject.data.api

import com.base.mvvmproject.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
suspend fun getUsers(): List<User>
}

