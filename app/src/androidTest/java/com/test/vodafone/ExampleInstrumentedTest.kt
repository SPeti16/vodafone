package com.test.vodafone

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
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
import org.junit.Rule
import retrofit2.Retrofit

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

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testLogin() {
        onView(withId(R.id.login_button)).perform(click())

        onView(withId(R.id.username_input)).check(matches(isDisplayed()))

        onView(withId(R.id.username_input)).perform(typeText("test@email.hu"), closeSoftKeyboard())

        onView(withId(R.id.password_input)).check(matches(isDisplayed()))

        onView(withId(R.id.password_input)).perform(typeText("1234"), closeSoftKeyboard())

        onView(withId(R.id.login_button)).perform(click())

        Thread.sleep(1000)

        onView(withId(R.id.toolbar_title)).check(matches(withText(R.string.offers_title)))

        var n = 0
        var search = true
        do {
            onView(withId(R.id.special_offers_list))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(n, click())
                )

            Thread.sleep(1000)

            onView(withId(R.id.toolbar_title))
                .check { view, _ ->
                    val titleText = (view as TextView).text.toString()
                    if (titleText.isNotEmpty()) {
                        search = false
                    }
                }

            if (search) {
                onView(isRoot()).perform(pressBack())
            }

            n++
        }while (search)
    }
}