package com.test.vodafone.server

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("offers")
    suspend fun getOffers(): List<OffersData>
    @GET("details")
    suspend fun getDetail(@Query("id") id: String): List<DetailsData>
}