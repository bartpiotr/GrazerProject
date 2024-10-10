package com.bartekjobhunt.grazerproject.repository

import com.bartekjobhunt.grazerproject.network.model.LoginResponse

interface GrazerRepository {
    suspend fun login(email: String, password: String): LoginResponse
}


