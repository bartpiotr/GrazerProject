package com.bartekjobhunt.grazerproject.repository

import com.bartekjobhunt.grazerproject.network.model.LoginRequest
import com.bartekjobhunt.grazerproject.network.model.User
import com.bartekjobhunt.grazerproject.network.service.GrazerService
import javax.inject.Inject
import javax.inject.Singleton

private const val BEARER = "Bearer"
private const val STATUS_OK = 200

@Singleton
class RetrofitGrazerRepository @Inject constructor(private val grazerService: GrazerService) :
    GrazerRepository {

    /**
     * Normally we'd have a token manager, authenticator and interceptors but for brevity we put that responsibility on the retrofit repository
     */
    private lateinit var token: String


    override suspend fun login(email: String, password: String): Boolean {
        try {
            return grazerService.login(LoginRequest(email, password)).run {
                if (status == STATUS_OK && auth != null) {
                    token = auth.data.token
                    true
                } else false
            }
        } catch (exception: Exception) {
            /*
             * Configuring okhttp, handling and logging network exceptions is a bigger subject than we won't cover here.
             */
            println("Exception: $exception")
            return false
        }
    }


    /**
     * The response indicates paging but the task doesn't say anything about header specifying the page we want to fetch.
     * also we don't bother here with things like handling missing or expired tokens by a token manager and an authenticator
     */
    override suspend fun getUsers(): List<User> =
        if (this::token.isInitialized)
            grazerService.getUsers("$BEARER $token").run {
                if(status == STATUS_OK) {
                    data.users
                } else {
                    throw IllegalStateException("Status: $status")
                }
            }
        else
            throw IllegalStateException("Token not initialized")

}
