package com.fggc.garden_mate.presentation.update_reportes

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.fggc.garden_mate.presentation.plantas.PlantasViewModel
 import com.fggc.garden_mate.presentation.update_reportes.components.UpdateReporteContext
import com.fggc.garden_mate.presentation.update_reportes.components.UpdateReporteTopBar

@Composable
fun UpdatePlantaScreen(
    viewModel: PlantasViewModel,
    navigateBack: () -> Unit
){
    LaunchedEffect(Unit){

    }
    Scaffold(
        topBar = {
            UpdateReporteTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            UpdateReporteContext(
                padding = padding,
                navigateBack = navigateBack
            )
        }
    )
}