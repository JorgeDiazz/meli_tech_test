package com.zemoga.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.base.interfaces.Logger
import com.zemoga.components.utils.viewBinding
import com.zemoga.home.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Represents main activity.
 *
 * This is the orchestrator of app's views.
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var logger: Logger

    private val binding by viewBinding(ActivityHomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        logger.d("HomeActivity started")
    }
}
