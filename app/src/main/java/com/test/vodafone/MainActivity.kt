package com.test.vodafone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
