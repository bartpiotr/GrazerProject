package com.bartekjobhunt.grazerproject.network.service

import com.bartekjobhunt.grazerproject.network.model.LoginRequest
import com.bartekjobhunt.grazerproject.network.model.LoginResponse
import com.bartekjobhunt.grazerproject.network.model.Users
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface GrazerService {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("/users")
    suspend fun getUsers(@Header("Authorization") token: String): Users
}
