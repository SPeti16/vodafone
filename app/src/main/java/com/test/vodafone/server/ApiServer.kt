package com.test.vodafone.server

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServerIModule {

    @Provides
    @Singleton
    fun provideApiServerI(): ApiServerI{
        return ApiServer()
    }
}


interface ApiServerI {
    val apiRepository: ApiRepository
}

class ApiServer() : ApiServerI {

    private val json = Json { ignoreUnknownKeys = true }


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://my-json-server.typicode.com/SPeti16/vodafone/")
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val apiRepository: ApiRepository by lazy {
        NetworkApiRepository(retrofitService)
    }
}