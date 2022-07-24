package com.example.enotes.api

import com.example.designstuff.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientSignupLogin {

    fun getRetrofit(): Retrofit {

        val logger = HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://localhost:7032/api/")
            .client(client)
            .build()

        return retrofit

    }

    fun getApiService(): ApiServiceSignupLogin{
        return getRetrofit().create(ApiServiceSignupLogin::class.java)
    }
}