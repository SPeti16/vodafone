package com.test.vodafone

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.test.vodafone.server.ApiService
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.test.vodafone", appContext.packageName)
    }
}

@RunWith(AndroidJUnit4::class)
class ApiServiceTest {

    private lateinit var apiService: ApiService
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setup() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/SPeti16/vodafone/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    @Test
    fun testGetOffers() = runBlocking {
        try {
            val offers = apiService.getOffers()
            assertTrue("Offers is not empty", offers.isNotEmpty())
            assertNotNull(offers[0].name)
        } catch (e: Exception) {
            fail("Failed to load offers: ${e.message}")
        }
    }

    @Test
    fun testGetDetail() = runBlocking {
        val offerId = "1"
        try {
            val detail = apiService.getDetail(offerId)
            assertNotNull("Detail should not be null", detail)
            assertEquals("Expected offer id", offerId, detail[0].id)
        } catch (e: Exception) {
            fail("Failed to load detail: ${e.message}")
        }
    }

    @Test
    fun testGetLogin() = runBlocking {
        try {
            val user = apiService.getLogin()
            assertTrue("User is not empty", user.isNotEmpty())
            assertNotNull(user[0].username)
        } catch (e: Exception) {
            fail("Failed to load user: ${e.message}")
        }
    }
}