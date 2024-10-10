package com.bartekjobhunt.grazerproject.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val status: Int,
    @SerialName("status_description")
    val statusDescription: String,
    val auth: Auth)

@Serializable
data class Auth(val data: Data)

@Serializable
data class Data(val token: String)

