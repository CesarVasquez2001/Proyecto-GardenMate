package com.fggc.garden_mate.presentation.update_reportes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fggc.garden_mate.core.Constants.Companion.ESTADO
import com.fggc.garden_mate.core.Constants.Companion.ESPECIE
import com.fggc.garden_mate.core.Constants.Companion.IMAGEN
import com.fggc.garden_mate.core.Constants.Companion.SENSOR_HUMEDAD
import com.fggc.garden_mate.core.Constants.Companion.SENSOR_TEMPERATURA
import com.fggc.garden_mate.core.Constants.Companion.NOMBRE
import com.fggc.garden_mate.core.Constants.Companion.UPDATE

@Composable
fun UpdateReporteContext(
    padding: PaddingValues,
    navigateBack: () -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Button(
            onClick = {
                navigateBack()
            }
        ) {
            Text(UPDATE)
        }
    }
}






