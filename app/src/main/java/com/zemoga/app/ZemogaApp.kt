package com.zemoga.app

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import androidx.paging.ExperimentalPagingApi
import com.app.core.CoreApp
import com.zemoga.app.di.components.AppComponent
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * Represents ZemogaApp application.
 *
 */
@HiltAndroidApp
@ExperimentalPagingApi
@ExperimentalSerializationApi
open class ZemogaApp : CoreApp() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        initializeStrictMode()
        super.onCreate()

        initializeComponent()
    }

    private fun initializeComponent() {
        appComponent = EntryPoints.get(this, AppComponent::class.java)
    }

    private fun initializeStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectAll()
                    .permitDiskReads()
                    .permitDiskWrites()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build()
            )
        }
    }
}
