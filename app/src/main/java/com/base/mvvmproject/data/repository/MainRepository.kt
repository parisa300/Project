package com.base.mvvmproject.data.repository
import com.base.mvvmproject.data.api.ApiHelper
class MainRepository (private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()
}