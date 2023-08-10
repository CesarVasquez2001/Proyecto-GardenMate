package com.fggc.garden_mate.presentation.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fggc.garden_mate.data.network.AmplifyService
import com.fggc.garden_mate.data.network.AmplifyServiceImpl
import com.fggc.garden_mate.domain.model.LoginState
import com.fggc.garden_mate.domain.model.SignUpState
import com.fggc.garden_mate.domain.model.VerificationCodeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val amplifyService: AmplifyService = AmplifyServiceImpl()

    lateinit var navigateTo: (String) -> Unit

    var loginState = mutableStateOf(LoginState())
        private set

    var signUpState = mutableStateOf(SignUpState())
        private set

    var verificationCodeState = mutableStateOf(VerificationCodeState())
        private set

    fun updateSignUpState(username: String? = null, email: String? = null, password: String? = null) {
        username?.let {
            signUpState.value = signUpState.value.copy(username = it)
            verificationCodeState.value = verificationCodeState.value.copy(username = it)
        }
        email?.let { signUpState.value = signUpState.value.copy(email = it) }
        password?.let { signUpState.value = signUpState.value.copy(password = it) }
    }

    fun updateLoginState(username: String? = null, password: String? = null) {
        username?.let { loginState.value = loginState.value.copy(username = it) }
        password?.let { loginState.value = loginState.value.copy(password = it) }
    }

    fun updateVerificationCodeState(code: String) {
        verificationCodeState.value = verificationCodeState.value.copy(code = code)
    }

    fun configureAmplify(context: Context) {
        amplifyService.configureAmplify(context)
    }

    fun showSignUp() {
        navigateTo("signUp")
    }

    fun showLogin() {
        navigateTo("login")
    }

    fun signUp() {
        amplifyService.signUp(signUpState.value) {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("verify")
            }
        }
    }

    fun verifyCode() {
        amplifyService.verifyCode(verificationCodeState.value) {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("login")
            }
        }
    }

    fun login() {
        amplifyService.login(loginState.value) {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("session")
            }
        }
    }

    fun logOut() {
        amplifyService.logOut {
            viewModelScope.launch(Dispatchers.Main) {
                navigateTo("login")
            }
        }
    }

    fun queryTodos(){
        amplifyService.queryTodos()
    }

    fun sendMessage1(){
        amplifyService.sendComando("b00a2c25-7309-43b3-9146-368b4c63cc11")
    }

    fun sendMessage2(){
        amplifyService.sendComando("1f7052eb-61f2-4572-b96f-a2f5ee7b7975")
    }

    fun sendMessage3(){
        amplifyService.sendComando("fbc0c87e-9428-47e0-b959-32cc4ada5d21")
    }

    fun sendMessage4(){
        amplifyService.sendComando("acf19aa5-58d8-44b5-85f1-058a88a53800")
    }

    fun sendMessage5(){
        amplifyService.sendComando("92524bf0-4ce9-451b-8909-7e84575646b7")
    }
}