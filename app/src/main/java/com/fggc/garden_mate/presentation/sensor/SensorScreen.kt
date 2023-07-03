package com.fggc.garden_mate.presentation.plantas

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.fggc.garden_mate.core.Constants.Companion.PLANTAS_SCREEN
import com.fggc.garden_mate.presentation.sensor.SensorViewModel
import com.fggc.garden_mate.presentation.sensor.components.AddSensorData
import com.fggc.garden_mate.presentation.sensor.components.AddSensorDataFloatingActionButton
import com.fggc.garden_mate.presentation.sensor.components.SensorContent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun SensorScreen(
    viewModel: SensorViewModel = hiltViewModel(),
    navigateToUpdateReporteScreen: (reporteId: Int) -> Unit,
    loginId: Int,

    ) {
    LaunchedEffect(Unit) {

    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(PLANTAS_SCREEN + " - sensor" + loginId)
            })
        },
        content = {
            SensorContent()
            AddSensorData(
                openDialog = viewModel.openDialog,
                closeDialog = {
                    viewModel.closeDialog()
                },
            ) {
            }
        },
        floatingActionButton = {
            AddSensorDataFloatingActionButton(
                openDialog = {
                    viewModel.openDialog()
                }
            )
        }
    )
}

