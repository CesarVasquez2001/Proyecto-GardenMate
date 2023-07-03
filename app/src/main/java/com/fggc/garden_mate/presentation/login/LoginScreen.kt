package com.fggc.garden_mate.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fggc.garden_mate.core.Constants
import com.fggc.garden_mate.domain.model.UserData

@Composable
fun LoginScreen(
    navigateToLoginPlantaScreen: () -> Unit,
    viewModel: LoginViewModel,
    ) {
    val context = LocalContext.current
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }

    Surface(

        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (showLoginForm.value) {
                Text(text = "Iniciar Sesion")
                UserForm(
                    isCreateAccount = false,
                    navigateToLoginPlantaScreen = navigateToLoginPlantaScreen,

                    )
                { email,username, password ->
                    navigateToLoginPlantaScreen
                    UserData.email = email
                    UserData.username = username
                    UserData.password = password
                    viewModel.signIn(UserData)
                }

            } else {
                Text(text = "Crea una cuenta")
                UserForm(
                    isCreateAccount = true,
                    navigateToLoginPlantaScreen = navigateToLoginPlantaScreen,
                )
                { email, username, password ->
                    UserData.email = email
                    UserData.username = username
                    UserData.password = password
                    viewModel.signUp(UserData)
                    viewModel.openDialog()
                }
                ConfirmCodeDialog(
                    viewModel = viewModel
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text1 =
                    if (showLoginForm.value) "No tienes cuenta?"
                    else "Ya tienes cuenta?"
                val text2 =
                    if (showLoginForm.value) "Registrate"
                    else "Inicia sesion"
                Text(text = text1)
                Text(text = text2,
                    modifier = Modifier
                        .clickable { showLoginForm.value = !showLoginForm.value }
                        .padding(start = 5.dp),
                    color = MaterialTheme.colors.secondaryVariant)
            }
        }
    }
}

@Composable
fun ConfirmCodeDialog(
    viewModel: LoginViewModel,
) {
    if (viewModel.openDialog) {
        var code by remember { mutableStateOf(Constants.NO_VALUE) }
        val focusRequester = FocusRequester()

        AlertDialog(
            onDismissRequest = { viewModel.closeDialog() },
            title = {
                Text(
                    "Confirm Code", textAlign = TextAlign.Center,fontSize = 15.sp,
                )
            },
            text = {
                Column() {
                    TextField(
                        value = code,
                        onValueChange = { code = it },
                        placeholder = {
                            Text("XXXXXX")
                        },
                        singleLine = true
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.confirmSignUp(UserData, code)
                        viewModel.closeDialog()
                    }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.closeDialog() }
                ) {
                    Text(Constants.DISMISS)
                }
            }

        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    navigateToLoginPlantaScreen: () -> Unit,
    isCreateAccount: Boolean = false,
    onDone: (String, String, String) -> Unit = { email, username, pwd -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val username = rememberSaveable{ mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val valido = remember(email.value, username.value, password.value) {
        username.value.trim().isNotEmpty() && password.value.trim().isNotEmpty() && (email.value.trim().isNotEmpty() || !isCreateAccount)
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        UserNameInput(
            usernameState = username
        )
        if (isCreateAccount){
            EmailInput(
                emailState = email
            )
        }
        PasswordInput(
            passwordState = password, labelId = "Contraseña", passwordVisible = passwordVisible
        )
        SubmitButton(
            navigateToLoginPlantaScreen = navigateToLoginPlantaScreen,
            textId = if (isCreateAccount) "Crear cuenta" else "Login",
            inputValido = valido,

            ) {
            onDone(
                email.value.trim(),
                username.value.trim(),
                password.value.trim(),
            )
            if (keyboardController != null) {
                keyboardController.hide()
            }

        }

    }
}

@Composable
fun UserNameInput(usernameState: MutableState<String>, labelId: String = "Username") {
    InputField(
        valueState = usernameState, labelId = labelId, keyboardType = KeyboardType.Text
    )
}


@Composable
fun SubmitButton(
    navigateToLoginPlantaScreen: () -> Unit,
    textId: String,
    inputValido: Boolean,
    onClic: () -> Unit
) {

    Button(
        onClick = navigateToLoginPlantaScreen,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValido
    ) {
        Text(
            text = textId, modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun PasswordInput(
    passwordState: MutableState<String>, labelId: String, passwordVisible: MutableState<Boolean>
) {
    val visualTransformation = if (passwordVisible.value) VisualTransformation.None
    else PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = {
            Text(
                text = labelId
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordState.value.isNotBlank()) {
                PasswordVisibleIcon(passwordVisible)
            } else null
        })
}

@Composable
fun PasswordVisibleIcon(passwordVisible: MutableState<Boolean>) {
    val image = if (passwordVisible.value) {
        Icons.Default.VisibilityOff
    } else {
        Icons.Default.Visibility
    }
    IconButton(onClick = {
        passwordVisible.value = !passwordVisible.value
    }) {
        Icon(
            imageVector = image, contentDescription = ""
        )
    }

}

@Composable
fun EmailInput(emailState: MutableState<String>, labelId: String = "Correo") {
    InputField(
        valueState = emailState, labelId = labelId, keyboardType = KeyboardType.Email
    )
}

@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}

