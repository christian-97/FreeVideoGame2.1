package com.example.freevideogame.retrofit

import com.example.freevideogame.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    var retrofit = Retrofit.Builder().baseUrl("https://www.freetogame.com/api/").addConverterFactory(
        GsonConverterFactory.create()).build()

    fun getInstace(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}