package com.fggc.lab03.presentation.reportes

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
import androidx.paging.compose.collectAsLazyPagingItems
import com.fggc.lab03.core.Constants.Companion.PLANTAS_SCREEN
import com.fggc.lab03.paging.SensorDataViewModel
import com.fggc.lab03.presentation.sensor.SensorViewModel
import com.fggc.lab03.presentation.sensor.components.AddSensorData
import com.fggc.lab03.presentation.sensor.components.AddSensorDataFloatingActionButton
import com.fggc.lab03.presentation.sensor.components.SensorContent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun SensorScreen(
    viewModel: SensorViewModel = hiltViewModel(),
    navigateToUpdateReporteScreen: (reporteId: Int) -> Unit,
    loginId: Int,

    ) {
    LaunchedEffect(Unit) {
        viewModel.getPostswithUser(loginId)
        /*
                viewModel.querySensorData()
        */
    }
    val reportes by viewModel.reportesUsers.collectAsState(
        initial = emptyList(),
    )

    Log.d("REPORTES", reportes.toString())


    val SensorDataViewModel = hiltViewModel<SensorDataViewModel>()
    val beers = SensorDataViewModel.SensorPagingFlow.collectAsLazyPagingItems()


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(PLANTAS_SCREEN + " - sensor" + loginId)
            })
        },
        content = {
            SensorContent(sensor = beers)
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
