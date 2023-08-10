package com.fggc.garden_mate.presentation.plantas

import android.annotation.SuppressLint
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import com.fggc.garden_mate.core.Constants
import com.fggc.garden_mate.core.Constants.Companion.PLANTAS_SCREEN
import com.fggc.garden_mate.presentation.plantas.components.AddPlantaFloatingActionButton
import com.fggc.garden_mate.presentation.plantas.components.PlantasContent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PlantasScreen(
    navigateToUpdatePlantaScreen: () -> Unit,
    viewModel: PlantasViewModel,
) {
//    LaunchedEffect(Unit) {
//        viewModel.getPostswithUser(loginId)
//    }
//    viewModel.createTodo()
//    viewModel.createTodo2()
//    viewModel.updateTodo()
//    viewModel.deleteTodo()

    viewModel.queryTodos()


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(PLANTAS_SCREEN)
            })
        },
        content = { padding ->
            PlantasContent(
                padding = padding,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.singOut()
                    navigateToUpdatePlantaScreen.invoke() },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Constants.ADD_PLANTA
                )
            }
        }
    )
}

