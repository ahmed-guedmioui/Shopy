package com.ag_apps.shopy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ag_apps.core.presentation.designsystem.ShopyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isCheckingAuth
            }
        }

        setContent {
            ShopyTheme {
                if (!viewModel.state.isCheckingAuth) {
                    NavigationRoot(
                        isLoggedIn = viewModel.state.isLoggedIn
                    )
                }
            }
        }
    }
}