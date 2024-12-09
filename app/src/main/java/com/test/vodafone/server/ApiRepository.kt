package com.test.vodafone.server

interface ApiRepository {
    suspend fun getOffers(): List<OffersData>
    suspend fun getDetail(id: String): List<DetailsData>
    suspend fun getLogin(): List<UserData>
}

class NetworkApiRepository(
    private val apiService: ApiService
) : ApiRepository{
    override suspend fun getOffers(): List<OffersData> = apiService.getOffers()
    override suspend fun getDetail(id: String): List<DetailsData> = apiService.getDetail(id)
    override suspend fun getLogin(): List<UserData> = apiService.getLogin()
}