package com.example.designstuff.api

import com.example.enotes.models.ImageModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

@JvmSuppressWildcards
interface ApiService {
    @POST("images/upload")
fun uploadPhoto(
        @Body body: RequestBody): Call<ImageModel>



}