package com.test.vodafone.server

import kotlinx.serialization.Serializable

@Serializable
data class OffersData(
    val id: String?,
    val rank: Int?,
    val isSpecial: Boolean,
    val name: String,
    val shortDescription: String
)

@Serializable
data class DetailsData(
    val id: String?,
    val name: String,
    val shortDescription: String,
    val description: String
)

@Serializable
data class UserData(
    val username: String,
    val password: String
)