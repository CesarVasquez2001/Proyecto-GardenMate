package com.fggc.garden_mate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fggc.garden_mate.core.Constants.Companion.PLANTA_ID
import com.fggc.garden_mate.data.network.Backend
import com.fggc.garden_mate.navigation.Screen.*
import com.fggc.garden_mate.presentation.plantas.PlantasScreen
import com.fggc.garden_mate.presentation.login.LoginScreen
import com.fggc.garden_mate.presentation.login.LoginViewModel
import com.fggc.garden_mate.presentation.plantas.PlantasViewModel
import com.fggc.garden_mate.presentation.plantas.SensorScreen
import com.fggc.garden_mate.presentation.update_reportes.UpdatePlantaScreen

@Composable
fun NavGraph(
    navController: NavHostController
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
            route = "${SensorScreen.route}/{loginId}",
            arguments = listOf(
                navArgument("loginId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val loginId = backStackEntry.arguments?.getInt("loginId") ?: 0

            SensorScreen(
                loginId = loginId,
                navigateToUpdateReporteScreen = { plantaId ->
                    navController.navigate(
                        "${LoginScreen.route}/${plantaId}"
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
        composable(
            route = "${UpdatePlantasScreen.route}/{$PLANTA_ID}",
            arguments = listOf(
                navArgument(PLANTA_ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val plantaId = backStackEntry.arguments?.getInt(PLANTA_ID) ?: 0
            UpdatePlantaScreen(
                viewModel = PlantasViewModel(backend = Backend),
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}