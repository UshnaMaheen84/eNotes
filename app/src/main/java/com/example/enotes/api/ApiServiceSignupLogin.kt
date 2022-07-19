package com.example.enotes.api

import com.example.enotes.models.request.LoginRequest
import com.example.enotes.models.request.RegisterRequest
import com.example.enotes.models.response.LoginResponse
import com.example.enotes.models.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceSignupLogin {

    @POST("/authentication")
    fun loninUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("Users/GetUsers")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

}