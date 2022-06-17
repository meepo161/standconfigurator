package ru.avem.standconfigurator.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.DelicateCoroutinesApi
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.UsersRepository


@OptIn(DelicateCoroutinesApi::class, ExperimentalComposeUiApi::class)
class LoginScreen : Screen {
    @Composable
    @OptIn(DelicateCoroutinesApi::class, ExperimentalComposeUiApi::class)
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current

        var login by remember { mutableStateOf(TextFieldValue("1")) }
        var loginErrorState by remember { mutableStateOf(false) }
        var passwordErrorState by remember { mutableStateOf(false) }
        var password by remember { mutableStateOf(TextFieldValue("1")) }

        val users = UsersRepository.users
        Scaffold(
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                            append("В")
                        }
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("ход")
                        }
                    }, fontSize = 30.sp)
                    Spacer(Modifier.size(16.dp))
                    OutlinedTextField(
                        singleLine = true,
                        value = login,
                        onValueChange = {
                            if (loginErrorState) {
                                loginErrorState = false
                            }
                            login = it
                        },
                        isError = loginErrorState,
                        modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                            keyEventNext(it, focusManager)
                        },
                        label = {
                            Text(text = "Введите логин*")
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = keyboardActionNext(focusManager)
                    )
                    if (loginErrorState) {
                        Text(text = "Обязательно", color = MaterialTheme.colors.primary)
                    }
                    Spacer(Modifier.size(16.dp))
                    var passwordVisibility by remember { mutableStateOf(true) }
                    OutlinedTextField(
                        singleLine = true,
                        value = password,
                        onValueChange = {
                            if (passwordErrorState) {
                                passwordErrorState = false
                            }
                            password = it
                        },
                        isError = passwordErrorState,
                        modifier = Modifier.fillMaxWidth().focusTarget().onPreviewKeyEvent {
                            keyEventNext(it, focusManager)
                        },
                        label = {
                            Text(text = "Введите пароль*")
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility = !passwordVisibility
                            }) {
                                Icon(
                                    imageVector = if (passwordVisibility) Icons.Default.Face else Icons.Default.Clear,
                                    contentDescription = "visibility",
                                    tint = MaterialTheme.colors.primary
                                )
                            }
                        },
                        visualTransformation = if (passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = keyboardActionNext(focusManager)
                    )
                    if (passwordErrorState) {
                        Text(text = "Обязательно", color = MaterialTheme.colors.primary)
                    }
                    Spacer(Modifier.size(16.dp))
                    Button(
                        onClick = {
                            when {
                                login.text.isEmpty() -> {
                                    loginErrorState = true
                                }
                                password.text.isEmpty() -> {
                                    passwordErrorState = true
                                }
                                else -> {
                                    val currentUser = users.firstOrNull() { user ->
                                        user.login == login.text && user.password == password.text
                                    }
                                    if (currentUser != null) {
                                        MainModel.currentUser = currentUser
                                        localNavigator.push(ProjectSelectorScreen())
                                    } else {
                                        loginErrorState = true
                                        passwordErrorState = true
                                    }
                                }
                            }

                        },
                        content = {
                            Text(text = "Вход", color = Color.White)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    )
//                    Spacer(Modifier.size(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        TextButton(onClick = {
                            localNavigator.push(RegistrationScreen())
                        }) {
                            Text(text = "Регистрация", color = MaterialTheme.colors.primary)
                        }
                    }
                }
            })
    }
}

