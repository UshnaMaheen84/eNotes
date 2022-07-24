package com.example.enotes.models.response

data class RegisterResponse (
    val user_id: Int,
    val name:String,
    val email: String,
    val password: String
)