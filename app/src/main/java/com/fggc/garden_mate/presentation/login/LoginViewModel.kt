package com.fggc.garden_mate.presentation.login
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fggc.garden_mate.data.network.Backend
import com.fggc.garden_mate.domain.model.UserData
import kotlinx.coroutines.launch

class LoginViewModel constructor
    (
    private val backend: Backend
    ) : ViewModel() {

    var openDialog by mutableStateOf(false)

    fun signUp(userData: UserData) = viewModelScope.launch{
        backend.signUp(userData)
    }

    fun confirmSignUp(userData: UserData, code: String) = viewModelScope.launch {
        backend.confirmSignUp(userData, code);
    }

    fun signIn(userData: UserData) = viewModelScope.launch {
        backend.signIn(userData);
    }

    fun signOut() = viewModelScope.launch {
        backend.signOut()
    }

    fun closeDialog() {
        openDialog = false
    }

    fun openDialog() {
        openDialog = true
    }
}

