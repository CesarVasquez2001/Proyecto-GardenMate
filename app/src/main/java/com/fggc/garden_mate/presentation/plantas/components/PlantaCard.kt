package com.fggc.garden_mate.presentation.plantas.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amplifyframework.datastore.generated.model.NoteData

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlantaCard(
    planta: NoteData,
    deletePlanta: () -> Unit,
    navigateToUpdatePlantaScreen: (plantaId: Int) -> Unit
){
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = 3.dp,
        onClick = {
            navigateToUpdatePlantaScreen(planta.id)
        }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column() {
                Text(planta.description)
                Text(planta.image)
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            DeleteIcon(
                deletePlanta = deletePlanta
            )
        }
    }
}

fun navigateToUpdatePlantaScreen(id: String?) {

}
