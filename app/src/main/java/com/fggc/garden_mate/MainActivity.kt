package com.fggc.garden_mate


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fggc.garden_mate.presentation.login.AuthViewModel
import com.fggc.garden_mate.presentation.login.LoginScreen
import com.fggc.garden_mate.presentation.login.SessionScreen
import com.fggc.garden_mate.presentation.login.SignUpScreen
import com.fggc.garden_mate.presentation.login.VerificationCodeScreen
import com.fggc.garden_mate.ui.theme.AsistenteCrudTheme


class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.configureAmplify(this)

        setContent {
            AsistenteCrudTheme {
                // A surface container using the 'background' color from the theme
                AppNavigator()
            }
        }
    }

    @Composable
    private fun AppNavigator() {
        val navController = rememberNavController()
        viewModel.navigateTo = {
            navController.navigate(it)
        }
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(viewModel = viewModel)
            }
            composable("signUp") {
                SignUpScreen(viewModel = viewModel)
            }
            composable("verify") {
                VerificationCodeScreen(viewModel = viewModel)
            }
            composable("session") {
                SessionScreen(viewModel = viewModel)
            }
        }
    }
}

