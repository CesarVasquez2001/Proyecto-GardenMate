package com.fggc.garden_mate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fggc.garden_mate.data.network.Backend
import com.fggc.garden_mate.navigation.Screen.*
import com.fggc.garden_mate.presentation.plantas.PlantasScreen
import com.fggc.garden_mate.presentation.login.LoginScreen
import com.fggc.garden_mate.presentation.login.LoginViewModel
import com.fggc.garden_mate.presentation.plantas.PlantasViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = LoginScreen.route
    ) {
        composable(
            route = LoginScreen.route
        ) {
            LoginScreen(
                viewModel = LoginViewModel(backend = Backend),
                navigateToLoginPlantaScreen = {
                    navController.navigate(
                        "${PlantasScreen.route}"
                    )
                }
            )
        }

        composable(
            route = "${PlantasScreen.route}",
        ) { backStackEntry ->
            PlantasScreen(
                viewModel = PlantasViewModel(backend = Backend),
                navigateToUpdatePlantaScreen = {
                    navController.navigate(
                        "${LoginScreen.route}"
                    )
                }
            )
        }

    }
}