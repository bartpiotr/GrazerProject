package com.bartekjobhunt.grazerproject.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Users(
    val status: Int,
    @SerialName("status_desc") val statusDescription: String,
    val data: UsersData
)

@Serializable
data class UsersData(val users: List<User>, val meta: Meta)

@Serializable
data class User(
    val name: String,
    @SerialName("date_of_birth") val dateOfBirth: Long,
    @SerialName("profile_image") val profileImageUrl: String
)

@Serializable
data class Meta(
    @SerialName("item_count") val itemCount: Long,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("current_page") val currentPage: Int
)
