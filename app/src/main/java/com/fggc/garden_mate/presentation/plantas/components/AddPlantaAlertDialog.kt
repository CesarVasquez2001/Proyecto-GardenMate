package com.fggc.garden_mate.presentation.plantas.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fggc.garden_mate.core.Constants.Companion.ADD
import com.fggc.garden_mate.core.Constants.Companion.ADD_PLANTA
import com.fggc.garden_mate.core.Constants.Companion.DISMISS
import com.fggc.garden_mate.core.Constants.Companion.NO_VALUE
import com.fggc.garden_mate.core.Constants.Companion.ESTADO
import com.fggc.garden_mate.core.Constants.Companion.ESPECIE
import com.fggc.garden_mate.core.Constants.Companion.IMAGEN
import com.fggc.garden_mate.core.Constants.Companion.SENSOR_HUMEDAD
import com.fggc.garden_mate.core.Constants.Companion.SENSOR_TEMPERATURA
import com.fggc.garden_mate.core.Constants.Companion.NOMBRE

@Composable
fun AddPlantaAlertDialog(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    loginId: Int
) {
    if (openDialog) {
        var nombre by remember { mutableStateOf(NO_VALUE) }
        var especie by remember { mutableStateOf(NO_VALUE) }
        var sensorHumedad by remember { mutableStateOf(NO_VALUE) }
        var sensorTemperatura by remember { mutableStateOf(NO_VALUE) }
        var imagen by remember { mutableStateOf(NO_VALUE) }
        var estado by remember { mutableStateOf(NO_VALUE) }
        var user_planta_id by remember {
            mutableStateOf(loginId)
        }
        val focusRequester = FocusRequester()

        AlertDialog(
            onDismissRequest = { closeDialog },
            title = {
                Text(
                    ADD_PLANTA, textAlign = TextAlign.Center,fontSize = 15.sp,
                )


            },


            text = {

                Column() {

                    TextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        placeholder = {
                            Text(NOMBRE)
                        },
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = especie,
                        onValueChange = { especie = it },
                        placeholder = {
                            Text(ESPECIE)
                        },
                        singleLine = true

                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = sensorHumedad,
                        onValueChange = { sensorHumedad = it },
                        placeholder = {
                            Text(SENSOR_HUMEDAD)
                        },
                        singleLine = true
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = sensorTemperatura,
                        onValueChange = { sensorTemperatura = it },
                        placeholder = {
                            Text(SENSOR_TEMPERATURA)
                        },
                        singleLine = true
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = imagen,
                        onValueChange = { imagen = it },
                        placeholder = {
                            Text(IMAGEN)
                        },
                        singleLine = true
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = estado,
                        onValueChange = { estado = it },
                        placeholder = {
                            Text(ESTADO)
                        },
                        singleLine = true
                    )


                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        closeDialog()
                    }) {
                    Text(ADD)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = closeDialog
                ) {
                    Text(DISMISS)
                }
            }

        )
    }
}