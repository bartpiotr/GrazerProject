package com.bartekjobhunt.grazerproject.repository

import com.bartekjobhunt.grazerproject.network.model.User

interface GrazerRepository {
    suspend fun login(email: String, password: String): Boolean

    suspend fun getUsers(): List<User>
}


