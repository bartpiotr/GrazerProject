package com.bartekjobhunt.grazerproject.repository

import com.bartekjobhunt.grazerproject.network.model.LoginRequest
import com.bartekjobhunt.grazerproject.network.model.LoginResponse
import com.bartekjobhunt.grazerproject.network.service.GrazerService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitGrazerRepository @Inject constructor(private val grazerService: GrazerService) :
    GrazerRepository {
    override suspend fun login(email: String, password: String): LoginResponse =
        grazerService.login(LoginRequest(email, password))
}
