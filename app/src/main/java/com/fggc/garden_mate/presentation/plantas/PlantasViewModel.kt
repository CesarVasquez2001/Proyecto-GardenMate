package com.fggc.garden_mate.presentation.plantas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fggc.garden_mate.core.Constants.Companion.NO_VALUE
import com.fggc.garden_mate.data.network.Backend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class
PlantasViewModel  (
    private val backend: Backend
) : ViewModel() {


    var openDialog by mutableStateOf(false)

    fun createTodo(){
        backend.createTodo()
    }

    fun createTodo2(){
        backend.createTodo2()
    }

    fun queryTodos(){
        backend.queryTodos()
    }

    fun updateTodo(){
        backend.updateTodos()
    }

    fun deleteTodo(){
        backend.deleteTodo()
    }


    fun closeDialog() {
        openDialog = false
    }

    fun openDialog() {
        openDialog = true
    }

}