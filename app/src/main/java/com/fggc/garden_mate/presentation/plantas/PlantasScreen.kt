package com.fggc.garden_mate.presentation.plantas

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.amplifyframework.datastore.generated.model.NoteData
import com.fggc.garden_mate.core.Constants.Companion.PLANTAS_SCREEN
import com.fggc.garden_mate.presentation.plantas.components.AddPlantaFloatingActionButton
import com.fggc.garden_mate.presentation.plantas.components.PlantasContent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PlantasScreen(
    navigateToUpdatePlantaScreen: (plantaId: Int) -> Unit,
    viewModel: PlantasViewModel,
) {
//    LaunchedEffect(Unit) {
//        viewModel.getPostswithUser(loginId)
//    }
    viewModel.createTodo()
    viewModel.createTodo2()
    viewModel.queryTodos()
    viewModel.updateTodo()
    viewModel.deleteTodo()



    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(PLANTAS_SCREEN)
            })
        },
        content = { padding ->
            PlantasContent(

            )
        },
        floatingActionButton = {
            AddPlantaFloatingActionButton(
                openDialog = {
                    viewModel.openDialog()
                }
            )
        }
    )
}

