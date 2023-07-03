package com.fggc.garden_mate.navigation

import com.fggc.garden_mate.core.Constants.Companion.LOGIN_SCREEN
import com.fggc.garden_mate.core.Constants.Companion.PLANTAS_SCREEN
import com.fggc.garden_mate.core.Constants.Companion.SENSOR_SCREEN
import com.fggc.garden_mate.core.Constants.Companion.UPDATE_PLANTAS_SCREEN

sealed class Screen(val route: String) {
    object PlantasScreen : Screen(PLANTAS_SCREEN)
    object UpdatePlantasScreen : Screen(UPDATE_PLANTAS_SCREEN)
    object LoginScreen : Screen(LOGIN_SCREEN)
    object SensorScreen : Screen(SENSOR_SCREEN)
}
